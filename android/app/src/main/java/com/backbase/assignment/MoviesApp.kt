package com.backbase.assignment

import android.app.Application
import com.backbase.assignment.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeKoin()

        initializeDebugTools()
    }

    private fun initializeKoin() = startKoin {
        androidContext(this@MoviesApp)
        androidLogger()
        modules(
            listOf(
                retrofitModule,
                moviesRepositoryModule,
                genreRepositoryModule,
                picassoModule,
                viewModelModule
            )
        )
    }

    private fun initializeDebugTools() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}