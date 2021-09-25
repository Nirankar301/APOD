package com.nir.apod.data

import android.content.Context
import com.nir.apod.model.APOD

class APODRepository(private val context: Context) : IAPODRepository{

    override suspend fun getAPOD(): APOD? {
      return null
    }
}