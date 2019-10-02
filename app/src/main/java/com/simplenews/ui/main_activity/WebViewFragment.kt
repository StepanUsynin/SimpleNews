package com.simplenews.ui.main_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.simplenews.R
import kotlinx.android.synthetic.main.fragment_web_view.*


class WebViewFragment(private val pageUrl: String) : Fragment() {

    private lateinit var webView: WebView

    companion object {

        fun newInstance(pageUrl: String): WebViewFragment {
            val fragment = WebViewFragment(pageUrl)
            val args = Bundle()
            args.putString("pageUrl", pageUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        webView = view.findViewById(R.id.webView) as WebView

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
            return view
        }

        webView.webChromeClient = setChromeClient()
        webView.loadUrl(pageUrl)

        return view
    }

    private fun setChromeClient(): WebChromeClient {
        return (object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, progress: Int) {

                if (progress < 100 && webViewProgress.visibility == View.GONE) {
                    webViewProgress.visibility = View.VISIBLE
                }

                webViewProgress.progress = progress

                if (progress == 100) {
                    webViewProgress.visibility = View.GONE
                }
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        webView.destroy()
    }
}
