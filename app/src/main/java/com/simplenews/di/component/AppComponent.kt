package com.simplenews.di.component

import com.simplenews.MainAdapter
import com.simplenews.di.module.DatabaseModule
import com.simplenews.di.module.NetworkModule
import com.simplenews.di.module.PicassoModule
import com.simplenews.ui.main_activity.MainPresenter
import com.simplenews.ui.splash_activity.SplashPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, PicassoModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(splashPresenter: SplashPresenter)
    fun inject(mainAdapter: MainAdapter)
}