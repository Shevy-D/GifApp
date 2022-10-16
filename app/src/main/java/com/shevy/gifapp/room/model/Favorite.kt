package com.shevy.gifapp.room.model

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.INTEGER
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_database")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "previewUrl", typeAffinity = TEXT) val downsized: String,

    @ColumnInfo(name = "url", typeAffinity = INTEGER) val original: String

)
