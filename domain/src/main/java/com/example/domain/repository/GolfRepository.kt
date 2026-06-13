package com.example.domain.repository

import com.example.domain.model.Player
import com.example.domain.model.Shot
import kotlinx.coroutines.flow.Flow

interface GolfRepository {

    fun observePlayers() : Flow<List<Player>>
    fun observeShots(playerId: String) : Flow<List<Shot>>

    suspend fun refreshPLayers()
    suspend fun refreshShots()
}
