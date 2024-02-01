package com.example.coinmarket.di

import com.example.coinmarket.model.apiManager.ApiService
import com.example.coinmarket.model.apiManager.BASE_URL
import com.example.coinmarket.model.apiManager.MainViewModel
import com.example.coinmarket.model.repository.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {

    single { createdRetrofit() }
    single { createService(get()) }

}

val viewModelModule = module {

    viewModel { MainViewModel(get()) }

}
val repositoryModule = module {

    single { Repository(get()) }

}

fun createdRetrofit() : Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}