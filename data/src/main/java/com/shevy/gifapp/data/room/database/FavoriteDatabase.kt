package com.shevy.gifapp.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shevy.gifapp.data.room.data.FavoriteDao
import com.shevy.gifapp.data.models.database.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var database: FavoriteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): FavoriteDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(context, FavoriteDatabase::class.java, "db")
                    //.fallbackToDestructiveMigration()
                    .build()
                database as FavoriteDatabase
            } else {
                database as FavoriteDatabase
            }
        }
    }
}