package com.simplenews.ui.splash_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.simplenews.R

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val presenter = SplashPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
