package com.simplenews.ui.main_activity

import android.os.Bundle
import android.view.View
import android.webkit.WebView
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
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.init(this)
        initAdapter()


        if (savedInstanceState == null) {
            mainPresenter.getNews()
        } else {

            mainViewModel.webViewVisibility.observe(this, Observer {
                mainWebView.visibility = it
            })

            mainViewModel.lastUrl.observe(this, Observer {
                mainWebView.loadUrl(it)
            })

            mainViewModel.news.observe(this, Observer {
                mainAdapter.setNews(it)
            })

            mainViewModel.currentPage.observe(this, Observer {
                mainPresenter.setCurrentPage(it)
            })

        }

    }


    private fun initAdapter() {
        mainAdapter = MainAdapter(this)
        val linearManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        newsRecyclerView.layoutManager = linearManager
        newsRecyclerView.adapter = mainAdapter

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
                mainPresenter.getNews()
            }
        })
    }


    override fun showNews(currentPage: Int, news: ArrayList<Article>) {
        mainAdapter.addAll(news)
        mainViewModel.currentPage.value = currentPage
        mainViewModel.news.value = mainAdapter.getNews()
    }

    override fun showLoadingError(error: String) {
        mainAdapter.showError()
        Toast.makeText(this, "Error receiving information from server", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        mainAdapter.showProgress()
    }

    override fun hideProgress() {
        mainAdapter.hideProgress()
    }

    override fun onTryAgainClicked() {
        mainAdapter.hideError()
        mainPresenter.getNews()
    }

    override fun onItemClicked(url: String) {

        mainWebView.visibility = View.VISIBLE
        mainWebView.loadUrl(url)

        mainViewModel.webViewVisibility.value = mainWebView.visibility
        mainViewModel.lastUrl.value = url
    }

    override fun onBackPressed() {
        if (mainWebView.isShown) {
            mainWebView.visibility = View.GONE
            mainViewModel.webViewVisibility.value = mainWebView.visibility
        } else {
            mainViewModel.currentPage.value = null
            mainViewModel.news.value = null
            super.onBackPressed()
        }
    }

}
