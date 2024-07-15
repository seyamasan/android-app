package com.example.seyamaapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MainEntity::class], version = 1)
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