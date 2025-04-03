package com.example.whatnow

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.whatnow.databinding.ArticleListItemBinding


class NewsAdapter(val a: Activity, val articles: ArrayList<Article>) :
    Adapter<NewsAdapter.NewsViewHolder>() {


    class NewsViewHolder(val binding: ArticleListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val b = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun getItemCount() = articles.size// single expression

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.articleText.text = articles[position].title
        Glide.with(holder.binding.articleImage.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImage)
        holder.binding.articleContainer.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articles[position].url))
            a.startActivity(intent)
        }
        holder.binding.shareFab.setOnClickListener {
            ShareCompat.IntentBuilder(a).setType("text/plain")
                .setChooserTitle("Share this article")
                .setText(articles[position].url)
                .startChooser()
        }


    }
}