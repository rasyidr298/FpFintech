package id.fp.fintech

import android.app.Application
import id.fp.core.di.databaseModule
import id.fp.core.di.networkModule
import id.fp.core.di.prefManagerModule
import id.fp.core.di.repositoryModule
import id.fp.fintech.di.useCaseModule
import id.fp.fintech.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MyApp : Application() {

    companion object {
        @get:Synchronized
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    prefManagerModule,
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}