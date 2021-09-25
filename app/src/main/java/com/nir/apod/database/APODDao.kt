package com.nir.apod.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface APODDao {
    @Query("SELECT * FROM aPODEntity")
    fun getAll(): List<APODEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(aPODEntity: APODEntity)

    @Insert
    suspend fun insertAll(vararg aPODEntity: APODEntity)

    @Delete
    fun delete(aPODEntity: APODEntity)
}