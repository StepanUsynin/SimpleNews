package com.simplenews.ui.main_activity

import com.simplenews.model.Article
import javax.inject.Inject

class MainContract {

    interface View {

        fun showNews(currentPage: Int, news: ArrayList<Article>)

        fun showProgress()

        fun hideProgress()

        fun showLoadingError(error: String)

    }

    interface Presenter {

        fun init(view: View)

        fun getNews()

        fun dispose()

    }

}