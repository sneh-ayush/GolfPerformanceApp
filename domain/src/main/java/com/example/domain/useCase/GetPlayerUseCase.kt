package com.example.domain.useCase

import com.example.domain.repository.GolfRepository

class GetPlayerUseCase (private val  repository: GolfRepository) {
    operator fun invoke() = repository.observePlayers()
}

class GetShotsUseCase(private val repository: GolfRepository) {
    operator fun invoke(playerId: String) = repository.observeShots(playerId)
}

class RefreshDataUseCase(private val repository: GolfRepository) {
    suspend operator fun invoke() {
        repository.refreshShots()
        repository.refreshPLayers()
    }
}