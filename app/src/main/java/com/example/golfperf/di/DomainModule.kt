package com.example.golfperf.di

import com.example.golfperf.domain.usecase.GetPlayersUseCase
import com.example.golfperf.domain.usecase.GetShotsUseCase
import com.example.golfperf.domain.usecase.RefreshDataUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPlayersUseCase(get()) }
    factory { GetShotsUseCase(get()) }
    factory { RefreshDataUseCase(get()) }
}
