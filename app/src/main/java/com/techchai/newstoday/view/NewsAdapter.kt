package com.techchai.newstoday.view

import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.techchai.newstoday.R
import com.techchai.newstoday.data.model.Headlines
import com.techchai.newstoday.common.AppUtils

class NewsAdapter(var context: FragmentActivity) :
    RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {
    var newsHeadlines: List<Headlines>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_view_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (newsHeadlines != null)
            newsHeadlines!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.titleView.text = newsHeadlines!!.get(position).title
        holder.contentView.text = newsHeadlines!!.get(position).content
        holder.timeView.text = AppUtils.formatDate(newsHeadlines!!.get(position).publishedAt)
        holder.sourceView.text = newsHeadlines!!.get(position).source!!.name
        holder.cardLayout.setOnClickListener(View.OnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(newsHeadlines!!.get(position).url))
        })

        holder.shareView.setOnClickListener {
            AppUtils.shareArticles(newsHeadlines!!.get(position).url, context)
        }

        if(TextUtils.isEmpty(newsHeadlines!!.get(position).urlToImage)) {
            holder.bannerView.visibility = View.GONE
        } else {
            holder.bannerView.visibility = View.VISIBLE
        }

        val options = RequestOptions()
        options.fitCenter()

        Glide.with(context)
            .load(newsHeadlines!!.get(position).urlToImage)
            .into(holder.bannerView)
    }

    fun setData(headlines: List<Headlines>) {
        newsHeadlines = headlines
        notifyDataSetChanged()
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleView: MaterialTextView
        var bannerView: ImageView
        var contentView: TextView
        var sourceView: TextView
        var timeView: TextView
        var shareView: ImageView
        var cardLayout: MaterialCardView

        init {
            titleView = view.findViewById(R.id.news_title)
            bannerView = view.findViewById(R.id.news_banner)
            contentView = view.findViewById(R.id.news_content)
            sourceView = view.findViewById(R.id.news_source)
            timeView = view.findViewById(R.id.news_date)
            shareView = view.findViewById(R.id.share)

            cardLayout = view.findViewById(R.id.cardview_lay)
        }
    }
}