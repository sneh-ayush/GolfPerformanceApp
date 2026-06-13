package com.example.golfperf.domain.usecase

import com.example.domain.repository.GolfRepository

class RefreshDataUseCase(
    private val repository: GolfRepository,
) {
    suspend operator fun invoke() {
        repository.refreshShots()
        repository.refreshPLayers()
    }
}
