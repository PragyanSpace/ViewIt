package com.example.viewit.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.viewit.data.Repository
import com.example.viewit.data.WordsDatabase
import com.example.viewit.data.WordsForModel

class HistoryViewModel(application: Application): AndroidViewModel(application) {
    public val readAllData:LiveData<List<WordsForModel>>

    init{
        val wordsDao= WordsDatabase.getDatabase(application).wordsDao()
        val repository=Repository(wordsDao)
        readAllData=repository.readAllData
    }
}