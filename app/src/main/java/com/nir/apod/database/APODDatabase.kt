package com.nir.apod.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * APOD database to store the apods
 */
@Database(entities = [APODEntity::class], version = 1)
abstract class APODDatabase : RoomDatabase() {
    abstract fun apodDao(): APODDao
}