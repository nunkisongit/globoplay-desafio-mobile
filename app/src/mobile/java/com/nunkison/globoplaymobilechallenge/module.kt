package com.nunkison.globoplaymobilechallenge

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidMobileModule = module {
    single { retrofitInstance() }
    single { sharedPrefsInstance() }
    factory { moviesRepositoryInstance() }
    viewModel { moviesViewModelInstance() }
    viewModel { moviesDetailViewModelInstance(it) }
}