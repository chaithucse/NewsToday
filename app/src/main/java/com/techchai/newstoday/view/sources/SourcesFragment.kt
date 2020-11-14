package com.techchai.newstoday.view.sources

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.techchai.newstoday.R
import dagger.hilt.android.AndroidEntryPoint

class SourcesFragment : Fragment() {

    lateinit var listView: RecyclerView
    lateinit var adapter: SourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_source, container, false)
        adapter = SourceAdapter()

        listView = view.findViewById(R.id.list_sources)
        listView.setHasFixedSize(true)
        listView.layoutManager = GridLayoutManager(activity, 2)
        listView.adapter = adapter

        return view
    }

    inner class SourceAdapter :
        RecyclerView.Adapter<SourceAdapter.CustomViewHolder>() {

        private val sourceIcon = intArrayOf(
            R.drawable.cnn_icon,
            R.drawable.bbc,
            R.drawable.bloomburg_icon,
            R.drawable.nbc_icon,
            R.drawable.reuters_icon,
            R.drawable.techcrunch_icon,
            R.drawable.wsj_icon,
            R.drawable.logo_icon_usa_today,
            R.drawable.verge_icon,
            R.drawable.abc_icon,
            R.drawable.business_insider_icon,
            R.drawable.fox_news_icon,
            R.drawable.geographic_icon,
            R.drawable.mtv_icon,
            R.drawable.ny_magazine_icon,
            R.drawable.washington_times_icon,
            R.drawable.washingtonpost_icon,
            R.drawable.nfl_logo,
            R.drawable.hindhu,
            R.drawable.toi
        )
        private val sourceName =
            arrayOf(
                "CNN",
                "BBC",
                "Bloomberg",
                "NBC",
                "Reuters",
                "TechCrunch",
                "WSJ",
                "USA Today",
                "Verge",
                "Abc News",
                "Business Insider",
                "Fox News",
                "National Geographic",
                "MTV",
                "New York Magazine",
                "Washington Times",
                "Washington Post",
                "NFL News",
                "The Hindu",
                "The Times of India"
            )

        private val sourceId =
            arrayOf(
                "cnn",
                "bbc-news",
                "bloomberg",
                "nbc-news",
                "reuters",
                "techcrunch",
                "the-wall-street-journal",
                "usa-today",
                "the-verge",
                "abc-news",
                "business-insider",
                "fox-news",
                "national-geographic",
                "mtv-news",
                "new-york-magazine",
                "the-washington-times",
                "the-washington-post",
                "nfl-news",
                "the-hindu",
                "the-times-of-india"
            )

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CustomViewHolder {
            return CustomViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.source_list_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return sourceName.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.sourceName.text = sourceName[position]
            holder.sourceIconView.setImageResource(sourceIcon[position])

            holder.cardLayout.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(activity, SourceListActivity::class.java)
                intent.putExtra("source_name", sourceName[position])
                intent.putExtra("source_id", sourceId[position])
                startActivity(intent)
            })
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var cardLayout: MaterialCardView
            var sourceIconView: ImageView
            var sourceName: TextView
            init {
                sourceIconView = view.findViewById(R.id.source_icon)
                sourceName = view.findViewById(R.id.source_name)
                cardLayout = view.findViewById(R.id.layout)
            }
        }
    }
}