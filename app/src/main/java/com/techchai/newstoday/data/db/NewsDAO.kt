package com.techchai.newstoday.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.techchai.newstoday.data.model.Headlines

/**
 * @author Chaitanya
 */
@Dao
interface NewsDAO {

    @Query("SELECT * from headlines Where country= :country AND type= :category")
    fun getTopHeadlines(country: String, category: String): LiveData<List<Headlines>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(headlines: List<Headlines>)

    @Query("Update headlines SET type = :category, country= :country WHERE id = :id")
    fun insertCountryAndCategory(id: Int, country: String, category: String)

    @Query("DELETE from headlines")
    fun deleteAll()
}