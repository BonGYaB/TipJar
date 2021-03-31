package com.example.tipjar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tipjar.database.entity.TipHistory

@Dao
interface TipHistoryDao {
    @Query("SELECT count(*) FROM tip_history") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
    suspend fun numberOfItemsInDB() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTipHistory(tip: TipHistory)

    @Update
    suspend fun updateUser(tip: TipHistory)

    @Delete
    suspend fun deleteTip(tip: TipHistory)

    @Query("DELETE FROM tip_history")
    suspend fun deleteAllTips()

    @Query("SELECT * FROM tip_history ORDER BY id ASC")
    fun readAllData(): LiveData<List<TipHistory>>
}