package com.example.tipjar.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tip_history")
data class TipHistory(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val amount: String,
        val people: String,
        val tip: String,
        val photo: String,
        val timestamp: String
) : Parcelable