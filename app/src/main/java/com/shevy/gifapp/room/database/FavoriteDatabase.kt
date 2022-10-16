package com.shevy.gifapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shevy.gifapp.room.data.FavoriteDao
import com.shevy.gifapp.room.model.Favorite

@Database(entities = arrayOf(Favorite::class), version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    /*private class FavoriteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val favoriteDao = database.favoriteDao()

                    // Delete all content here.
                    favoriteDao.deleteAll()

                    // Add sample favorites.
                    var favorite = Favorite(0, "previewUrl", "url")
                    favoriteDao.insertFavoriteToRoomDatabase(favorite)
                    favorite = Favorite(0, "previewUrl1", "url1")
                    favoriteDao.insertFavoriteToRoomDatabase(favorite)

                    // TODO: Add your own words!
                    favorite = Favorite(0, "previewUrl2", "url2")
                    favoriteDao.insertFavoriteToRoomDatabase(favorite)
                }
            }
        }
    }*/

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java,
                        "favorite_database"
                    )
                        .build()
                    INSTANCE = instance
                    return instance
                }
        }
/*        fun getDatabase(context: Context, scope: CoroutineScope): FavoriteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_database"
                ).addCallback(FavoriteDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }*/
    }
}