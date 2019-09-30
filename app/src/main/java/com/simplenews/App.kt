package com.simplenews

import android.app.Application
import com.simplenews.di.component.AppComponent
import com.simplenews.di.component.DaggerAppComponent
import com.simplenews.di.module.*

open class App : Application() {

    companion object {

        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .okHttpModule(OkHttpModule())
            .contextModule(ContextModule(applicationContext))
            .databaseModule(DatabaseModule())
            .build()
    }


}