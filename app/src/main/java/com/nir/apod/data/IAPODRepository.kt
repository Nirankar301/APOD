package com.nir.apod.data

import com.nir.apod.model.APOD

interface IAPODRepository {
    /**
     * Retrieves news headlines. It is configured to provide latest news for the country India
     * @return Headlines
     */
    suspend fun getAPOD(): APOD?
}