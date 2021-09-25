package com.nir.apod.module

import com.nir.apod.viewmodel.APODViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        APODViewModel(get(), get())
    }
}