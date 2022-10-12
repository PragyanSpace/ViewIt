package com.example.viewit.data

import androidx.lifecycle.LiveData

class Repository(private val wordsDao: WordsDao) {
    val readAllData:LiveData<List<WordsForModel>> =wordsDao.readAllData()

    suspend fun add(word: WordsForModel)
    {
            wordsDao.add(word)
    }

}