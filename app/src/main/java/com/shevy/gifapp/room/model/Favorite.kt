package com.shevy.gifapp.room.model

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.INTEGER
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/*@Entity(tableName = "favorite_database")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "previewUrl", typeAffinity = TEXT)
    val downsized: String,

    @ColumnInfo(name = "url", typeAffinity = TEXT)
    val original: String
)*/

@Entity(tableName = "favorite_database")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val downsized: String = "",
    @ColumnInfo
    val original: String = ""
) : Serializable
