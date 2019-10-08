package com.simplenews.ui.main_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplenews.MainAdapter
import com.simplenews.MainScrollListener
import com.simplenews.R
import com.simplenews.callback.MainAdapterCallback
import com.simplenews.model.Article
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View, MainAdapterCallback {

    private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private val mainPresenter = MainPresenter()
    private val mainAdapter: MainAdapter = MainAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        mainPresenter.init(this)

        if (savedInstanceState == null) {
            mainPresenter.getNewsFromDatabase()
        } else {

            mainViewModel.news.observe(this, Observer {
                mainAdapter.setNews(it)
            })

            mainViewModel.currentPage.observe(this, Observer {
                mainPresenter.setCurrentPage(it)
            })

        }

    }

    private fun initAdapter() {

        val linearManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        newsRecyclerView.layoutManager = linearManager
        newsRecyclerView.adapter = mainAdapter

        //newsRecyclerView.post { mainAdapter.notifyItemInserted(mainAdapter.getNewsFromServer().size - 1) }

        newsRecyclerView.addOnScrollListener(object :
            MainScrollListener(linearManager) {
            override fun isLoading(): Boolean {
                return mainPresenter.isProgress
            }

            override fun isLastPage(): Boolean {
                return mainPresenter.isLastPage
            }

            override fun isError(): Boolean {
                return mainPresenter.isError
            }

            override fun loadMoreItems() {
                mainPresenter.getNewsFromServer()
            }
        })
    }

    override fun saveCurrentPage(currentPage: Int) {
        mainViewModel.currentPage.value = currentPage
    }


    override fun showNews(news: ArrayList<Article>) {
        mainAdapter.addAll(news)
        mainViewModel.news.value = mainAdapter.getNews()
    }

    override fun showProgress() {
        mainAdapter.showProgress()
    }

    override fun hideProgress() {
        mainAdapter.hideProgress()
    }

    override fun showError(error: String) {
        mainAdapter.showError()
        Toast.makeText(this, "Error receiving information from server", Toast.LENGTH_SHORT).show()
    }

    override fun hideError() {
        mainAdapter.hideError()
    }

    override fun onTryAgainClicked() {
        mainPresenter.hideError()
        mainPresenter.getNewsFromServer()
    }

    override fun onItemClicked(url: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, WebViewFragment.newInstance(url))
            .commit()
    }

    override fun onBackPressed() {

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentHolder)

        if (fragment != null) {

            supportFragmentManager
                .beginTransaction()
                .remove(fragment)
                .commit()

        } else {
            super.onBackPressed()
        }
    }

}
