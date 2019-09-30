package com.simplenews.di.module

import android.content.Context
import com.simplenews.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ContextModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

}