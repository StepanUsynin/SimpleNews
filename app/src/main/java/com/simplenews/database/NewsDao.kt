package com.simplenews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.simplenews.model.Article


@Dao
interface NewsDao {

    @Query("SELECT * FROM article")
    fun all(): List<Article>

    @Query("SELECT COUNT(*) from article")
    fun count(): Int

    @Insert
    fun insert(article: Article)

    @Query("SELECT * FROM article WHERE author=:author AND title=:title AND description=:description")
    fun getArticle(author: String, title: String, description: String): Article?
}