package com.techchai.newstoday.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.techchai.newstoday.common.AppUtils
import com.techchai.newstoday.data.db.NewsDAO
import com.techchai.newstoday.data.db.NewsDatabase
import com.techchai.newstoday.data.io.ApiClient
import com.techchai.newstoday.data.io.ApiService
import com.techchai.newstoday.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * News repository
 * @author Chaitanya
 */
class NewsRepository(private val application: Application) {

    var headlines: MutableLiveData<List<Headlines>>? = null
    var searchList: MutableLiveData<List<Headlines>>? = null
    var sourceList: MutableLiveData<List<Source>>? = null
    var newsSourcesList: MutableLiveData<List<Headlines>>? = null

    lateinit var newsDao: NewsDAO

    init {
        headlines = MutableLiveData()
        searchList = MutableLiveData()
        sourceList = MutableLiveData()
        newsSourcesList = MutableLiveData()
        newsDao = NewsDatabase.getDatabase(application)!!.newsDao()
    }

    fun getTopHeadlines(country: String, category: String): LiveData<List<Headlines>>? {
        fetchHeadlines(country, category)
        return headlines
    }

    fun getNewsSources(country: String): LiveData<List<Source>>? {
        fetchNewsSources(country)
        return sourceList
    }

    fun getNewsFromSources(source: String): LiveData<List<Headlines>>? {
        fetchNewsFromSources(source)
        return newsSourcesList
    }

    fun getSearchResults(query: String): LiveData<List<Headlines>>? {
        val newsService = ApiClient.getRetrofitInstance(application).create(ApiService::class.java)
        newsService.searchEverything(query, "publishedAt", "en")
            .enqueue(object : Callback<NewsHeadlines> {
                override fun onFailure(call: Call<NewsHeadlines>, t: Throwable) {
                    Log.d("CHAIT", "onFailure")
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<NewsHeadlines>,
                    response: Response<NewsHeadlines>
                ) {
                    Log.d("CHAIT", "onResponse")
                    searchList!!.value = response.body()!!.articles
                }
            })

        return searchList
    }

    fun fetchHeadlines(country: String, category: String) {
        val newsService = ApiClient.getRetrofitInstance(application).create(ApiService::class.java)
        newsService.getTopHeadlines(country, category).enqueue(object : Callback<NewsHeadlines> {
            override fun onFailure(call: Call<NewsHeadlines>, t: Throwable) {
                Log.d("CHAIT", "onFailure: ${t.printStackTrace()}")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsHeadlines>, response: Response<NewsHeadlines>) {
                headlines!!.value = response.body()!!.articles
                Log.d("CHAIT", "response.body()!!.articles: " + response.body())
                GlobalScope.launch(Dispatchers.IO) {
                    newsDao.insertAllArticles(response.body()!!.articles)
                    newsDao.insertCountryAndCategory(0, country, category)
                }
            }
        })
    }

    fun fetchNewsSources(country: String) {
        val newsService = ApiClient.getRetrofitInstance(application).create(ApiService::class.java)
        newsService.getNewsSources(country).enqueue(object : Callback<NewsSource> {
            override fun onFailure(call: Call<NewsSource>, t: Throwable) {
                Log.d("CHAIT", "onFailure")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsSource>, response: Response<NewsSource>) {
                sourceList!!.value = response.body()!!.sources
            }
        })
    }

    fun fetchNewsFromSources(source: String) {
        val newsService = ApiClient.getRetrofitInstance(application).create(ApiService::class.java)
        newsService.getNewsFromSource(source).enqueue(object : Callback<NewsHeadlines> {
            override fun onFailure(call: Call<NewsHeadlines>, t: Throwable) {
                Log.d("CHAIT", "onFailure")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsHeadlines>, response: Response<NewsHeadlines>) {
                newsSourcesList!!.value = response.body()!!.articles
            }
        })
    }
}