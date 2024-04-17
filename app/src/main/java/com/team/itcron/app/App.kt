package com.team.itcron.app

import android.app.Application
import com.team.itcron.di.dataModule
import com.team.itcron.di.domainModule
import com.team.itcron.di.presentationModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            modules(
                listOf(dataModule, domainModule, presentationModule)
            )
        }
    }
}