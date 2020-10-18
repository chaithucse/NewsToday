package com.techchai.newstoday.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SourceTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSourceValuesList(source: Source?): String? {
        if (source == null) {
            return null
        }

        val listType = object : TypeToken<Source>() {

        }.type

        return gson.toJson(source, listType)
    }

    @TypeConverter
    fun toSourceValuesList(someObjects: String): Source {

        val listType = object : TypeToken<Source>() {

        }.type

        return gson.fromJson(someObjects, listType)
    }

}