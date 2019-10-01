package com.simplenews.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.simplenews.model.Article


class MainViewModel : ViewModel() {

    var currentPage: MutableLiveData<Int> = MutableLiveData()

    var webViewVisibility: MutableLiveData<Int> = MutableLiveData()

    var lastUrl: MutableLiveData<String> = MutableLiveData()

    var news: MutableLiveData<ArrayList<Article>> = MutableLiveData()

}