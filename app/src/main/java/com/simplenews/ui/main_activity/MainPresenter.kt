package com.simplenews.ui.main_activity

import android.util.Log
import com.simplenews.App
import com.simplenews.Constants
import com.simplenews.api.NewsApiInterface
import com.simplenews.database.NewsDao
import com.simplenews.model.Article
import com.simplenews.model.ResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter : MainContract.Presenter {

    companion object {
        private val TAG: String = MainPresenter::class.java.name
    }

    @Inject
    lateinit var api: NewsApiInterface

    @Inject
    lateinit var newsDao: NewsDao

    private lateinit var view: MainContract.View

    private var subscription: Disposable? = null

    private var currentPage = 1

    var isLastPage = false
    var isProgress = false
    var isError = false

    override fun init(view: MainContract.View) {
        this.view = view
        App.appComponent.inject(this)
    }

    override fun getNews() {
        Log.d("MainActivity", "Download $currentPage page")
        showProgress()
        subscription = api
            .getNews(page = currentPage.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { responseModel -> responseProcessing(responseModel) },
                { error -> errorProcessing(error.message.toString()) }
            )

    }

    private fun responseProcessing(responseModel: ResponseModel) {
        for (article: Article in responseModel.articles) {
            val entity = newsDao.getArticle(
                article.author.toString(),
                article.title.toString(),
                article.description.toString()
            )
            if (entity == null) {
                newsDao.insert(article)
            }
        }

        isLastPage = currentPage == Constants.TOTAL_PAGES

        currentPage += 1

        if (isError) hideError()
        hideProgress()

        view.showNews(currentPage, responseModel.articles)
    }

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    private fun errorProcessing(error: String) {
        hideProgress()
        isError = true
        view.showError(error)
    }

    fun hideError() {
        isError = false
        view.hideError()
    }

    private fun showProgress() {
        isProgress = true
        view.showProgress()
    }

    private fun hideProgress() {
        isProgress = false
        view.hideProgress()
    }

    override fun dispose() {
        subscription?.dispose()
    }

}