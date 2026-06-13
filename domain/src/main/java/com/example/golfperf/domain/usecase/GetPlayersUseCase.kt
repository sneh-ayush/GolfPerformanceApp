package com.example.golfperf.domain.usecase

import com.example.domain.repository.GolfRepository

class GetPlayersUseCase(
    private val repository: GolfRepository,
) {
    operator fun invoke() = repository.observePlayers()
}
