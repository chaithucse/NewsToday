package com.techchai.newstoday.data

import com.techchai.newstoday.BuildConfig
import com.techchai.newstoday.data.io.ApiService
import com.techchai.newstoday.data.model.NewsHeadlines
import io.reactivex.Single
import javax.inject.Inject

/**
 * News repository
 * @author Chaitanya
 */
class NewsRepository @Inject constructor(private val newsService: ApiService) {
    fun getTopHeadlines(country: String, category: String): Single<NewsHeadlines>? {
        return newsService.getTopHeadlines(country, category, BuildConfig.API_KEY)
    }

    fun getNewsFromSources(source: String): Single<NewsHeadlines>? {
        return newsService.getNewsFromSource(source, BuildConfig.API_KEY)
    }

    fun getSearchResults(query: String): Single<NewsHeadlines>? {
        return newsService.searchEverything(query, "publishedAt", "en", BuildConfig.API_KEY)
    }
}