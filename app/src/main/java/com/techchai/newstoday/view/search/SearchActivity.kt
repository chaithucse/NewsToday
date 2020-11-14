package com.techchai.newstoday.view.search

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techchai.newstoday.R
import com.techchai.newstoday.data.model.NewsHeadlines
import com.techchai.newstoday.view.NewsAdapter
import com.techchai.newstoday.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Search news activity
 * @author Chaitanya
 */

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var listView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var searchView: EditText
    val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.navigationIcon!!.setColorFilter(
            resources.getColor(R.color.textColorPrimary),
            PorterDuff.Mode.SRC_ATOP
        )

        searchView = findViewById(R.id.search_query)
        searchView.requestFocus()

        adapter = NewsAdapter(this)

        searchView.setOnEditorActionListener { v, actionId, event ->
            newsViewModel.getSearchResults(v.text.toString().trim())!!.observe(
                this,
                Observer { searchResults: NewsHeadlines -> adapter.setData(searchResults.articles) })
            searchView.hideKeyboard()
            true
        }

        searchView.setOnClickListener(View.OnClickListener {
            searchView.showKeyboard()
        })

        listView = findViewById(R.id.list)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = adapter
    }

    fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }

    fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
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