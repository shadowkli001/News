package com.example.news_new2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news_new2.R
import com.example.news_new2.databinding.ItemArticleBinding
import com.example.news_new2.model.Article


class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                // det kan vara return oldItem.url == oldItem.url
                return oldItem.title == oldItem.title
            }
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return newItem.title == newItem.title
            }
        }
    }

    class ArticleViewHolder(var view: ItemArticleBinding) : RecyclerView.ViewHolder(view.root)

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemArticleBinding>(inflater, R.layout.item_article, parent,
        false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.view.article = article

        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                article.let{article ->
                    it(article)
                }
            }
        }
        holder.view.ivShare.setOnClickListener{
            onShareNewsClick?.let {
                article.let{it1 ->
                    it(it1)
                }
            }
        }
        holder.view.ivSave.setOnClickListener{
           if(holder.view.ivSave.tag.toString().toInt() == 0) {
               holder.view.ivSave.tag = 1
               holder.view.ivSave.setImageDrawable(it.resources.getDrawable(R.drawable.ic_saved))
               onArticleSaveClick?.let {
                   if(article != null) {
                       it(article)
                   }
               }
           }
            else {
               holder.view.ivSave.tag = 0
               holder.view.ivSave.setImageDrawable(it.resources.getDrawable(R.drawable.ic_save))
               onArticleSaveClick?.let {
                   if(article != null) {
                       it(article)
                   }
               }
           }
            onArticleSaveClick?.let {
                article?.let{it1 -> it(it1)}
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    var isSave = false

    override fun getItemId(position: Int) = position.toLong()

    private var onItemClickListener : ((Article) -> Unit)? = null
    private var onArticleSaveClick : ((Article) -> Unit)? = null
    private var onArticleDeleteClick : ((Article) -> Unit)? = null
    private var onShareNewsClick : ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }
    fun onSaveClickListener(listener: ((Article) -> Unit)) {
        onArticleSaveClick = listener
    }
    fun onDeleteClickListener(listener: ((Article) -> Unit)) {
        onArticleDeleteClick = listener
    }
    fun onShareClickListener(listener: ((Article) -> Unit)) {
        onShareNewsClick = listener
    }
}