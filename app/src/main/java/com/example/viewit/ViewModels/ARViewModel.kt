package com.example.viewit.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewit.data.Repository
import com.example.viewit.data.WordsDatabase
import com.example.viewit.data.WordsForModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ARViewModel(application: Application): AndroidViewModel(application){

    private val repository: Repository
    init {
        val userDao= WordsDatabase.getDatabase(application).wordsDao()
        repository=Repository(userDao)
    }

    fun addModel(wordsForModel: WordsForModel) {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.add(wordsForModel)
        }
    }
}