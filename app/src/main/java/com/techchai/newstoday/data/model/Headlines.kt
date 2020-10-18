package com.techchai.newstoday.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "headlines")
data class Headlines(
    @PrimaryKey
    var id: Int,
    var type: String?,
    var country: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?,
    var author: String?,
    var source: Source?
)