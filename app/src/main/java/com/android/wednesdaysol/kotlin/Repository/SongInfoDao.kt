package com.android.wednesdaysol.kotlin.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.wednesdaysol.kotlin.Models.ResultModel


@Dao
interface SongInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultModel: List<ResultModel>)

    @get:Query("SELECT * from post_info ORDER BY artistId ASC")
    val allPosts: LiveData<List<ResultModel>>

    @Query("DELETE FROM post_info")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(resultModel: List<ResultModel>)
}