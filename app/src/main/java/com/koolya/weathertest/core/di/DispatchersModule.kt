package com.koolya.weathertest.core.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class Dispatcher {
    IO,
    MAIN,
    DEFAULT,
}

val DispatchersModule = module {

    single<CoroutineDispatcher>(named(Dispatcher.IO)) { Dispatchers.IO }

    single<CoroutineDispatcher>(named(Dispatcher.MAIN)) { Dispatchers.Main }

    single<CoroutineDispatcher>(named(Dispatcher.DEFAULT)) { Dispatchers.Default }

}
