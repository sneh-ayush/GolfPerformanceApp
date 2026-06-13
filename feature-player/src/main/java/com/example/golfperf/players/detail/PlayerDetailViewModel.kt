package com.example.golfperf.players.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.golfperf.domain.model.Shot
import com.example.golfperf.domain.usecase.GetShotsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class DetailUiState(
    val shots: List<Shot> = emptyList(),
    val avgSpeed: Double = 0.0,
    val avgDistance: Double = 0.0,
)

class PlayerDetailViewModel(
    private val getShotsUseCase: GetShotsUseCase,
) : ViewModel() {

    private val playerId = MutableStateFlow("")

    val uiState: StateFlow<DetailUiState> = playerId
        .filter { it.isNotBlank() }
        .flatMapLatest { id -> getShotsUseCase(id) }
        .map { shots ->
            DetailUiState(
                shots = shots,
                avgSpeed = if (shots.isEmpty()) 0.0 else shots.map { it.ballSpeed }.average(),
                avgDistance = if (shots.isEmpty()) 0.0 else shots.map { it.carryDistance }.average(),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState(),
        )

    fun setPlayer(id: String) {
        playerId.value = id
    }
}