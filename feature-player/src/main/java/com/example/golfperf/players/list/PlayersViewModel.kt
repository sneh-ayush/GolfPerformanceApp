package com.example.golfperf.players.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Player
import com.example.golfperf.domain.usecase.GetPlayersUseCase
import com.example.golfperf.domain.usecase.RefreshDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PlayersUiState(
    val players: List<Player> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

class PlayersViewModel(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val refreshDataUseCase: RefreshDataUseCase,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<PlayersUiState> = combine(
        getPlayersUseCase(),
        searchQuery,
        errorMessage,
    ) { players, query, error ->
        val filteredPlayers = if (query.isBlank()) {
            players
        } else {
            players.filter { it.club.contains(query, ignoreCase = true) }
        }
        PlayersUiState(
            players = filteredPlayers,
            isLoading = false,
            errorMessage = error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayersUiState(),
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                refreshDataUseCase()
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Failed to refresh data"
            }
        }
    }

    fun onSearch(query: String) {
        searchQuery.value = query
    }
}
