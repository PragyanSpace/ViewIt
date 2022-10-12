package com.example.viewit.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(word:WordsForModel)

    @Query("Select * from WordsForModel order by words ASC")
    fun readAllData():LiveData<List<WordsForModel>>


}