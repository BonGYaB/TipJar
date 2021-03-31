package com.example.tipjar.database.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.repository.TipRepository
import androidx.lifecycle.AndroidViewModel
import com.example.tipjar.database.TipDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class TipViewModel @ViewModelInject constructor(
//        private val repository: TipRepository
//    ): ViewModel() {

class TipViewModel(application: Application): AndroidViewModel(application) {
    private var repository: TipRepository
    val readAllData: LiveData<List<TipHistory>>
    init {
        val tipHistory = TipDatabase.getDatabase(application).tipHistory()
        repository = TipRepository(tipHistory)
        readAllData = repository.readAllData
    }

    fun getInstance() : String {
        return this.toString()
    }

    suspend fun databaseSize() : Int {
        return repository.numberOfItemsInDB()
    }

    fun readAllData(): LiveData<List<TipHistory>> {
        return repository.readAllData
    }

    fun addTip(tip: TipHistory) {
        viewModelScope.launch {
            repository.addTip(tip)
        }
    }

    fun updateTip(tip: TipHistory) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTip(tip)
        }
    }

    fun deleteTip(tip: TipHistory) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTip(tip)
        }
    }

    fun deleteAllTips() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }
}