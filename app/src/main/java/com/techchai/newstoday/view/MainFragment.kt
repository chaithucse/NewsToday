package com.techchai.newstoday.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.techchai.newstoday.R
import com.techchai.newstoday.common.AppConstants
import com.techchai.newstoday.data.model.Headlines
import com.techchai.newstoday.viewmodel.NewsViewModel

class MainFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var adapter: NewsAdapter
    private lateinit var coinViewModel: NewsViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var county: String
    private lateinit var mChipGroup: ChipGroup;
    var categoryType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity!!.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE)
        county = sharedPreferences.getString(AppConstants.PREF_COUNTRY_VALUE, "us")!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        mChipGroup = view.findViewById(R.id.chip_group)
        adapter = NewsAdapter(activity!!)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.registerOnSharedPreferenceChangeListener(this)

        mChipGroup.setOnCheckedChangeListener { group, checkedId: Int ->
            val chip: Chip? = view.findViewById(checkedId)
            chip?.let {
                when(chip.id) {
                    R.id.chip_news -> categoryType = ""
                    R.id.chip_tech -> categoryType = "technology"
                    R.id.chip_business -> categoryType = "business"
                    R.id.chip_enter -> categoryType = "entertainment"
                    R.id.chip_sports -> categoryType = "sports"
                    R.id.chip_health -> categoryType = "health"
                    R.id.chip_science -> categoryType = "science"
                }
            }
            if(adapter!=null) {
                fetchData(categoryType)
                adapter.notifyDataSetChanged();
            }

        }
        coinViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        val listView: RecyclerView = view.findViewById(R.id.category_list)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(activity)
        listView.adapter = adapter

        fetchData(categoryType)
        return view
    }

    fun fetchData(type: String) {
        coinViewModel.getNewsHeadlines(county, type)!!.observe(
            this,
            Observer { coinNews: List<Headlines> -> adapter.setData(coinNews) })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d("RAVU", "Preference changed: ")
        county = sharedPreferences!!.getString(key, "us")!!
        fetchData(categoryType)
    }
}