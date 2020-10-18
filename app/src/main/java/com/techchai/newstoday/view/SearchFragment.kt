package com.techchai.newstoday.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techchai.newstoday.R
import com.techchai.newstoday.data.model.Headlines
import com.techchai.newstoday.viewmodel.NewsViewModel

class SearchFragment : Fragment() {
    val LOG_TAG = SearchFragment::class.simpleName

    lateinit var listView: RecyclerView
    lateinit var adapter: NewsAdapter
    lateinit var searchView: EditText

    private lateinit var coinViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView")

        val view: View =   inflater.inflate(R.layout.activity_search, container, false)

        val toolbar: Toolbar = view.findViewById(R.id.search_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        searchView = view.findViewById(R.id.search_query)
        searchView.requestFocus()

        adapter = NewsAdapter(activity!!)
        coinViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        searchView.setOnEditorActionListener { v, actionId, event ->
            coinViewModel.getSearchResults(v.text.toString().trim())!!.observe(
                activity!!,
                Observer { coinNews: List<Headlines> -> adapter.setData(coinNews) })
            searchView.hideKeyboard()
            true
        }

        searchView.setOnClickListener(View.OnClickListener {
            Log.d(LOG_TAG, "setOnClickListener")
            searchView.showKeyboard()
        })

        listView = view.findViewById(R.id.list)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(activity)
        listView.adapter = adapter

        return view
    }

    fun View.showKeyboard() {
        Log.d(LOG_TAG, "showKeyboard")
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }

    fun View.hideKeyboard() {
        Log.d(LOG_TAG, "hideKeyboard")
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}