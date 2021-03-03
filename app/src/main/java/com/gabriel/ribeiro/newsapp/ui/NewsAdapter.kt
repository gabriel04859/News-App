package com.gabriel.ribeiro.newsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabriel.ribeiro.newsapp.R
import com.gabriel.ribeiro.newsapp.models.Article

class NewsAdapter(private val onArticleClickListener: OnArticleClickListener) : RecyclerView.Adapter<NewsAdapter.HeadLinesViewHolder>() {

    inner class HeadLinesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageViewArticle : ImageView = itemView.findViewById(R.id.imageViewArticleItem)
        val textViewSourceItem : TextView = itemView.findViewById(R.id.textViewSourceItem)
        val textViewTitleItem : TextView = itemView.findViewById(R.id.textViewTitleItem)
        val textViewDescriptionItem : TextView = itemView.findViewById(R.id.textViewDescriptionItem)
        val textViewPublishedAtItem : TextView = itemView.findViewById(R.id.textViewPublishedAtItem)

    }

    private val differUtilCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadLinesViewHolder {
        return HeadLinesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_headlines, parent, false))
    }

    override fun onBindViewHolder(holder: HeadLinesViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.imageViewArticle)
            holder.textViewSourceItem.text = article.source.name
            holder.textViewDescriptionItem.text = article.description
            holder.textViewTitleItem.text = article.title
            holder.textViewPublishedAtItem.text = article.publishedAt
        }
        holder.itemView.setOnClickListener {
            onArticleClickListener.onArticleClicked(article)
        }



    }

    interface OnArticleClickListener {
        fun onArticleClicked (article: Article)

    }

    override fun getItemCount() = differ.currentList.size
}