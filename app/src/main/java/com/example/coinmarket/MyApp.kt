package com.example.coinmarket

import android.app.Application
import com.example.coinmarket.di.NetworkModule
import com.example.coinmarket.di.repositoryModule

import com.example.coinmarket.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(NetworkModule, repositoryModule, viewModelModule)
        }
    }


}