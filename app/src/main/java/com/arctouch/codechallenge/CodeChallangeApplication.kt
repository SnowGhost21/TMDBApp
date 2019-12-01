package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.dependency.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CodeChallangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CodeChallangeApplication)
            modules(appModule)
        }
    }
}