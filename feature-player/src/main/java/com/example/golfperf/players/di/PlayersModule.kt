package com.example.golfperf.players.di

import com.example.golfperf.players.detail.PlayerDetailViewModel
import com.example.golfperf.players.list.PlayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playersModule = module {
    viewModel { PlayersViewModel(get(), get()) }
    viewModel { PlayerDetailViewModel(get()) }
}
