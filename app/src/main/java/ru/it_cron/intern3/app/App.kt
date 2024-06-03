package ru.it_cron.intern3.app

import android.app.Application
import ru.it_cron.intern3.di.dataModule
import ru.it_cron.intern3.di.domainModule
import ru.it_cron.intern3.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(dataModule, domainModule, presentationModule)
            )
        }
    }
}