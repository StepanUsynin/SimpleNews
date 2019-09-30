package com.simplenews.database

import androidx.room.RoomDatabase
import androidx.room.Database
import com.simplenews.model.Article


@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}