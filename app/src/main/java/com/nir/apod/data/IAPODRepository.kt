package com.nir.apod.data

import com.nir.apod.model.APOD

interface IAPODRepository {
    /**
     * Retrieves apod
     * @return [APOD]
     */
    suspend fun getAPOD(): APOD?
}