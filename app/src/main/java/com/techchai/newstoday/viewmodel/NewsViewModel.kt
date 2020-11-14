package com.techchai.newstoday.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techchai.newstoday.data.NewsRepository
import com.techchai.newstoday.data.model.NewsHeadlines
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * View model to provide data to views
 *
 * @author Chaitanya
 */
class NewsViewModel @ViewModelInject constructor(private val repository: NewsRepository) :
    ViewModel() {
    private var disposable: CompositeDisposable? = null
    private val headlines: MutableLiveData<NewsHeadlines> = MutableLiveData<NewsHeadlines>()
    private val searchResults: MutableLiveData<NewsHeadlines> = MutableLiveData<NewsHeadlines>()
    private val sources: MutableLiveData<NewsHeadlines> = MutableLiveData<NewsHeadlines>()

    private val responseError = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    init {
        disposable = CompositeDisposable()
    }

    fun getError(): LiveData<Boolean?>? {
        return responseError
    }

    fun getLoading(): LiveData<Boolean?>? {
        return loading
    }

    fun getNewsHeadlines(country: String, category: String): LiveData<NewsHeadlines>? {
        loading.value = false
        disposable?.add(
            repository.getTopHeadlines(country, category)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsHeadlines>() {
                    override fun onSuccess(value: NewsHeadlines) {
                        responseError.value = false
                        headlines.value = value
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace();
                        responseError.value = true
                        loading.value = false
                    }
                })
        )
        return headlines
    }

    fun getSearchResults(query: String): LiveData<NewsHeadlines>? {
        loading.value = false
        disposable?.add(
            repository.getSearchResults(query)!!.subscribeOn(
                Schedulers.io()
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsHeadlines>() {
                    override fun onSuccess(value: NewsHeadlines) {
                        responseError.value = false
                        searchResults.value = value
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        responseError.value = true
                        loading.value = false
                    }
                })
        )
        return searchResults
    }

    fun getNewsFromSources(source: String): LiveData<NewsHeadlines>? {
        loading.value = false
        disposable?.add(
            repository.getNewsFromSources(source)!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsHeadlines>() {
                    override fun onSuccess(value: NewsHeadlines) {
                        responseError.value = false
                        sources.value = value
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        responseError.value = true
                        loading.value = false
                    }
                })
        )
        return sources
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}