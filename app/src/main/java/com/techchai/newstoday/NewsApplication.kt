package com.techchai.newstoday

import android.app.Application

class NewsApplication : Application() {
    private lateinit var application: Application

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}