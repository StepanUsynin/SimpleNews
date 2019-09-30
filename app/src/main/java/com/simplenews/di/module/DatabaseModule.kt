package com.simplenews.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import com.simplenews.database.NewsDao
import com.simplenews.database.NewsDatabase


@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java, "news-db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

}