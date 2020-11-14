package com.techchai.newstoday.data.io

import com.techchai.newstoday.data.model.NewsHeadlines
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API end points
 * @author Chaitanya
 */
interface ApiService {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apikey: String
    ): Single<NewsHeadlines>

    @GET("everything")
    fun getNewsFromSource(
        @Query("sources") sources: String,
        @Query("apiKey") apikey: String
    ): Single<NewsHeadlines>

    @GET("everything")
    fun searchEverything(
        @Query("q") query: String,
        @Query("sortBy") sort: String,
        @Query("language") lan: String,
        @Query("apiKey") apikey: String
    ): Single<NewsHeadlines>
}