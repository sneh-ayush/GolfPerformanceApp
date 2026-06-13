package com.example.golfperf.domain.usecase

import com.example.domain.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.Flow

class ObserveConnectivityUseCase(
    private val connectivityObserver: ConnectivityObserver,
) {
    operator fun invoke(): Flow<ConnectivityObserver.Status> = connectivityObserver.observe()
}
