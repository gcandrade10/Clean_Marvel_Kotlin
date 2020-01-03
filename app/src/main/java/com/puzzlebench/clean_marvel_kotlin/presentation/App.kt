package com.puzzlebench.clean_marvel_kotlin.presentation

import android.app.Application
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule,useCaseModule))
        }
    }
}
