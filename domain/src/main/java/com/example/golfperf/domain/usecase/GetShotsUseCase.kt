package com.example.golfperf.domain.usecase

import com.example.domain.repository.GolfRepository

class GetShotsUseCase(
    private val repository: GolfRepository,
) {
    operator fun invoke(playerId: String) = repository.observeShots(playerId)
}
