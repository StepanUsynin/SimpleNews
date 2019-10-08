package com.simplenews.ui.splash_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.simplenews.R
import kotlinx.android.synthetic.main.activity_splash.*
import android.content.Intent
import com.simplenews.ui.main_activity.MainActivity


class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val presenter = SplashPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.init(this)
        presenter.loadNews("1")
    }

    override fun showLoading() {
        splashProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        splashProgress.visibility = View.GONE
    }

    override fun showLoadingError(error: String) {
        Toast.makeText(this, "Error receiving information from server", Toast.LENGTH_SHORT).show()
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
