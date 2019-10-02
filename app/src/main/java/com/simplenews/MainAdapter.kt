package com.simplenews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simplenews.callback.MainAdapterCallback
import com.simplenews.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.error_item.view.*
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.progress_item.view.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainAdapter(private val callback: MainAdapterCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_ERROR = 2
    }

    @Inject
    lateinit var picasso: Picasso

    private var isLoaderVisible = false
    private var isError = false

    private var news: ArrayList<Article> = ArrayList()

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            VIEW_TYPE_ITEM -> viewHolder = NewsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.news_item,
                    parent,
                    false
                )
            )
            VIEW_TYPE_LOADING -> viewHolder = ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.progress_item,
                    parent,
                    false
                )
            )
            VIEW_TYPE_ERROR -> viewHolder = ErrorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.error_item,
                    parent,
                    false
                )
            )
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> (holder as NewsViewHolder).bind(news[position])
            VIEW_TYPE_LOADING -> (holder as ProgressViewHolder).bind()
            VIEW_TYPE_ERROR -> (holder as ErrorViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setNews(news: ArrayList<Article>) {
        this.news = news
        notifyDataSetChanged()
    }


    fun addAll(newsList: ArrayList<Article>) {
        for (news in newsList) {
            add(news)
        }
        Log.d("MainAdapter", "Total news: ${news.size}")
    }

    private fun add(article: Article) {
        news.add(article)
        notifyItemInserted(news.size - 1)
    }

    fun showError() {
        isError = true
        add(Article())
    }

    fun hideError() {
        isError = false
        removeNullItem()
    }

    fun showProgress() {
        isLoaderVisible = true
        add(Article())
    }

    fun hideProgress() {
        isLoaderVisible = false
        removeNullItem()
    }

    fun getNews() : ArrayList<Article> {
        return news
    }

    private fun removeNullItem() {
        val position = news.size - 1
        news.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == news.size - 1 && isError) VIEW_TYPE_ERROR
        else if (position == news.size - 1 && isLoaderVisible) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: Article) {

            itemView.titleTextView.text = article.title
            itemView.descriptionTextView.text = article.description
            itemView.dateTextView.text = article.publishedAt

            if (article.urlToImage != null && article.urlToImage.isNotEmpty()) {
                picasso.load(article.urlToImage).into(itemView.imageView)
            }

            itemView.itemLayout.setOnClickListener{
                if (article.url != null) callback.onItemClicked(article.url)
            }
        }

    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.progressBar.visibility = View.VISIBLE
        }
    }

    inner class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.tryAgainTextView.setOnClickListener {
                callback.onTryAgainClicked()
            }
        }
    }

}
