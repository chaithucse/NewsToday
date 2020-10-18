package com.techchai.newstoday.data.io

import com.techchai.newstoday.data.model.NewsHeadlines
import com.techchai.newstoday.data.model.NewsSource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String, @Query("category") category: String): Call<NewsHeadlines>

    @GET("sources")
    fun getNewsSources(@Query("country") country: String): Call<NewsSource>

    @GET("everything")
    fun getNewsFromSource(@Query("sources") sources: String): Call<NewsHeadlines>

    @GET("everything")
    fun searchEverything(@Query("q") query: String, @Query("sortBy") sort: String, @Query("language") lan: String): Call<NewsHeadlines>
}