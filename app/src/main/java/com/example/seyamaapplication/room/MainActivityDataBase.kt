package com.example.seyamaapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MainEntity::class], version = 1, exportSchema = false)
abstract class MainActivityDataBase : RoomDatabase() {
    abstract fun mainDao(): MainDao

    companion object {
        fun buildDatabase(context: Context): MainActivityDataBase {
            return Room.databaseBuilder(
                context,
                MainActivityDataBase::class.java, "main_database"
            ).build()
        }
    }
}