package com.backbase.assignment.di

import com.backbase.assignment.data.genres.GenreRepository
import com.backbase.assignment.data.genres.GenreRepositoryImpl
import com.backbase.assignment.data.movies.MoviesRepository
import com.backbase.assignment.data.movies.MoviesRepositoryImpl
import org.koin.dsl.module

val moviesRepositoryModule = module {
    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
}

val genreRepositoryModule = module {
    single<GenreRepository> { GenreRepositoryImpl(get()) }
}