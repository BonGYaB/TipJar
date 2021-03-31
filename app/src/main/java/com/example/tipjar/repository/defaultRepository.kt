package com.example.tipjar.repository

import androidx.lifecycle.LiveData
import com.example.tipjar.database.dao.TipHistoryDao
import com.example.tipjar.database.entity.TipHistory
import javax.inject.Inject

class defaultRepository
    @Inject constructor(
            private val tipHistoryDao: TipHistoryDao
    ) {

//    override suspend fun numberOfItemsInDB() {
//        return tipHistoryDao.numberOfItemsInDB()
//    }

    val readAllData: LiveData<List<TipHistory>> = tipHistoryDao.readAllData()

    suspend fun addTip(tip: TipHistory) {
        tipHistoryDao.addTipHistory(tip)
    }

    suspend fun updateTip(tip: TipHistory) {
        tipHistoryDao.updateUser(tip)
    }

    suspend fun deleteTip(tip: TipHistory) {
        tipHistoryDao.deleteTip(tip)
    }

    suspend fun deleteAllData() {
        tipHistoryDao.deleteAllTips()
    }
}