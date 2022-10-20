package com.shevy.gifapp.data.models.database

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_database")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "previewUrl", typeAffinity = TEXT)
    val downsized: String,

    @ColumnInfo(name = "url", typeAffinity = TEXT)
    val original: String
)
