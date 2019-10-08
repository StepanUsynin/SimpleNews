package com.simplenews.ui.main_activity

import com.simplenews.model.Article
import javax.inject.Inject

class MainContract {

    interface View {

        fun showNews(news: ArrayList<Article>)

        fun saveCurrentPage(currentPage: Int)

        fun showProgress()

        fun hideProgress()

        fun showError(error: String)

        fun hideError()

    }

    interface Presenter {

        fun init(view: View)

        fun getNewsFromServer()

        fun getNewsFromDatabase()

        fun dispose()

    }

}