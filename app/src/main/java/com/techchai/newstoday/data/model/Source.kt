package com.techchai.newstoday.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data class Source(
    @PrimaryKey
    var id: String,
    var name: String?,
    var url: String?,
    var category: String?,
    var description: String?
)