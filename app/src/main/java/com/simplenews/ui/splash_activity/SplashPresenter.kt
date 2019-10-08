package com.simplenews.ui.splash_activity

import com.simplenews.App
import com.simplenews.api.NewsApiInterface
import com.simplenews.database.NewsDao
import com.simplenews.model.Article
import com.simplenews.model.ResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenter : SplashContract.Presenter {

    @Inject
    lateinit var api: NewsApiInterface

    @Inject
    lateinit var newsDao: NewsDao

    private lateinit var view: SplashContract.View

    private var subscription: Disposable? = null

    override fun init(view: SplashContract.View) {
        this.view = view
        App.appComponent.inject(this)
    }

    override fun loadNews(page: String) {
        view.showLoading()
        subscription = api
            .getNews(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { responseModel -> responseProcessing(responseModel) },
                { error -> errorProcessing(error.message.toString()) }
            )
    }

    private fun responseProcessing(responseModel: ResponseModel) {

        view.showLoading()

        for (article: Article in responseModel.articles) {
            newsDao.insert(article)
        }

        view.hideLoading()
        view.openMainActivity()
    }

    private fun errorProcessing(error: String) {
        view.hideLoading()
        view.showLoadingError(error)
    }

    override fun dispose() {
        subscription?.dispose()
    }


}