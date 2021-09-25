package com.nir.apod.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class APODEntity(
    @PrimaryKey @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "explanation") val explanation: String?,
    @ColumnInfo(name = "url") val url: String?
)
