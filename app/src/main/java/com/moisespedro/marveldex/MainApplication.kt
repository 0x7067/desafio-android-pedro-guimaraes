package com.moisespedro.marveldex

import android.app.Application
import com.moisespedro.marveldex.di.responseHandlerModule
import com.moisespedro.marveldex.di.viewModelModule
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(responseHandlerModule, viewModelModule)
        }
    }
}