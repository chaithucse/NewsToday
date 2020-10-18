package com.techchai.newstoday.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techchai.newstoday.R
import com.techchai.newstoday.data.model.Headlines
import com.techchai.newstoday.viewmodel.NewsViewModel

class SourceListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.source_list_news)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val title = intent.getStringExtra("source_name")
        val sourceID = intent.getStringExtra("source_id")
        toolbar.title = title

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = NewsAdapter(this)

        val listView: RecyclerView = findViewById(R.id.list)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = adapter

        val coinViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        coinViewModel.getNewsFromSources(sourceID)!!.observe(
            this,
            Observer { coinNews: List<Headlines> -> adapter.setData(coinNews) })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}