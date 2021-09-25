package com.nir.apod.module

import com.nir.apod.data.APODRepository
import com.nir.apod.data.IAPODRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<IAPODRepository> { APODRepository(androidContext()) }

    single { Dispatchers.IO }
}