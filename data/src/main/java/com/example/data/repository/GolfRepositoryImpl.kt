package com.example.data.repository

import com.example.domain.model.Player
import com.example.domain.model.Shot
import com.example.domain.repository.GolfRepository

class GolfRepositoryImpl : GolfRepository {

    override suspend fun getPlayers(): List<Player> = emptyList()

    override suspend fun getPlayerById(playerId: Long): Player? = null

    override suspend fun getShotsByPlayerId(playerId: Long): List<Shot> = emptyList()

    override suspend fun getShotById(shotId: Long): Shot? = null
}
