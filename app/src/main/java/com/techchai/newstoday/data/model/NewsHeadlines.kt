package com.techchai.newstoday.data.model

data class NewsHeadlines(
    var totalResults: String,
    var articles: List<Headlines>
)