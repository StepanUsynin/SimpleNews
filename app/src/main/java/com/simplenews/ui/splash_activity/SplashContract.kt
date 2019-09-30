package com.simplenews.ui.splash_activity


class SplashContract {

    interface View {

        fun showLoading()

        fun hideLoading()

        fun showLoadingError(error: String)

    }

    interface Presenter {

        fun init(view: View)

        fun loadNews(page: String)

        fun dispose()

    }

}