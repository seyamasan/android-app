package com.example.seyamaapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MainDao {
    @Insert
    fun insert(saveText: MainEntity)

    @Query("SELECT * FROM main_table")
    fun selectAllData(): List<MainEntity>

    @Update
    fun updateColumnValue(newValue: MainEntity)

    @Query("DELETE FROM main_table")
    fun deleteAll()
}