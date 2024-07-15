package com.example.seyamaapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_table")
data class MainEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "save_text") val saveText: String?
)