package com.techchai.newstoday.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.techchai.newstoday.data.NewsRepository
import com.techchai.newstoday.data.model.Headlines

/**
 * View model to provide data to views
 *
 * @author Chaitanya
 */
class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: NewsRepository = NewsRepository(application)

    fun getNewsHeadlines(country: String, category: String): LiveData<List<Headlines>>? {
        return repository.getTopHeadlines(country, category)
    }

    fun getSearchResults(query: String): LiveData<List<Headlines>>? {
        return repository.getSearchResults(query)
    }

    fun getNewsFromSources(source: String): LiveData<List<Headlines>>? {
        return repository.getNewsFromSources(source)
    }
}