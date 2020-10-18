package com.techchai.newstoday.view

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techchai.newstoday.R
import com.techchai.newstoday.data.model.Headlines
import com.techchai.newstoday.viewmodel.NewsViewModel

class SearchActivity : AppCompatActivity() {

    lateinit var listView: RecyclerView
    lateinit var adapter: NewsAdapter
    lateinit var searchView: EditText

    private lateinit var coinViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.navigationIcon!!.setColorFilter(resources.getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP)

        searchView = findViewById(R.id.search_query)
        searchView.requestFocus()

        adapter = NewsAdapter(this)
        coinViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        searchView.setOnEditorActionListener { v, actionId, event ->
            coinViewModel.getSearchResults(v.text.toString().trim())!!.observe(
                this,
                Observer { coinNews: List<Headlines> -> adapter.setData(coinNews) })
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
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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