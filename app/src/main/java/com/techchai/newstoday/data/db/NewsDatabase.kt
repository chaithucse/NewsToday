package com.techchai.newstoday.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techchai.newstoday.data.model.Headlines
import com.techchai.newstoday.data.model.Source
import com.techchai.newstoday.data.model.SourceTypeConverter

/**
 * Data base
 * @author Chaitanya
 */
@Database(entities = arrayOf(Headlines::class, Source::class), version = 1)
@TypeConverters(SourceTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDAO

    companion object {
        private var instance: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase? {

            if (null == instance) {
                synchronized(NewsDatabase::class.java) {
                    instance = Room.databaseBuilder(context, NewsDatabase::class.java, "news.db").build()
                }
            }
            return instance
        }
    }
}