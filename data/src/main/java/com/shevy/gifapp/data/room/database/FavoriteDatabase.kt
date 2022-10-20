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
/*    private class FavoriteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val favoriteDao = database.getFavoriteDao()

                    // Delete all content here.
                    favoriteDao.deleteAll()

                    // Add sample favorites.
                    var favorite = Favorite(
                        0,
                        downsized = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy-downsized.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy-downsized.gif&ct=g",
                        original = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy.gif&ct=g"
                    )
                    favoriteDao.insertFavoriteToRoomDatabase(favorite)
                    favorite = Favorite(
                        0,
                        downsized = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy-downsized.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy-downsized.gif&ct=g",
                        original = "https://media3.giphy.com/media/gZQ7NsdTXM86ZLPpdN/giphy.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy.gif&ct=g"
                    )
                    favoriteDao.insertFavoriteToRoomDatabase(favorite)

                    // TODO: Add your own favorite!
                    favorite = Favorite(
                        0,
                        downsized = "https://media2.giphy.com/media/cCuBNLbZqclxkpWyby/giphy-downsized.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy-downsized.gif&ct=g",
                        original = "https://media2.giphy.com/media/cCuBNLbZqclxkpWyby/giphy.gif?cid=f491d3b4hubhkuj6x7029ygp1b96bf16bw1t8q84myzqewf9&rid=giphy.gif&ct=g"
                    )
                    favoriteDao.insertFavoriteToRoomDatabase(favorite)
                }
            }
        }
    }*/
}