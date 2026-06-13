package com.example.golfperf.players.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.connectivity.ConnectivityObserver
import com.example.domain.model.Player
import com.example.golfperf.domain.usecase.GetPlayersUseCase
import com.example.golfperf.domain.usecase.ObserveConnectivityUseCase
import com.example.golfperf.domain.usecase.RefreshDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
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
    private val observeConnectivityUseCase: ObserveConnectivityUseCase,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val isRefreshing = MutableStateFlow(true)
    private val errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<PlayersUiState> = combine(
        getPlayersUseCase(),
        searchQuery,
        isRefreshing,
        errorMessage,
    ) { players, query, refreshing, error ->
        val filtered = if (query.isBlank()) {
            players
        } else {
            players.filter { player ->
                player.club.contains(query, ignoreCase = true) ||
                    player.name.contains(query, ignoreCase = true)
            }
        }
        PlayersUiState(
            players = filtered,
            isLoading = refreshing && players.isEmpty(),
            errorMessage = error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayersUiState(),
    )

    init {
        observeConnectivity()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            observeConnectivityUseCase()
                .filter { it == ConnectivityObserver.Status.Available }
                .distinctUntilChanged()
                .collect { refresh() }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshing.value = true
            try {
                refreshDataUseCase()
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Failed to refresh data"
            } finally {
                isRefreshing.value = false
            }
        }
    }

    fun onSearch(query: String) {
        searchQuery.value = query
    }
}
