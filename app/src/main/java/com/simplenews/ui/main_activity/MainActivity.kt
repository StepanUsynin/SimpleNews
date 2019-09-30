package com.simplenews.ui.main_activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.simplenews.MainAdapter
import com.simplenews.MainScrollListener
import com.simplenews.R
import com.simplenews.callback.MainAdapterCallback
import com.simplenews.model.Article
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, MainAdapterCallback {

    private val mainPresenter = MainPresenter()
    private lateinit var mainAdapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter.init(this)
        initAdapter()
        mainPresenter.getNews()
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


    override fun showNews(news: ArrayList<Article>) {
        mainAdapter.addAll(news)
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
    }

    override fun onBackPressed() {
        if (mainWebView.isShown) {
            mainWebView.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

}
