package com.example.data.repository

import com.example.data.GolfApi
import com.example.data.local.dao.GolfDao
import com.example.data.mapper.PlayerMapper.toDomain
import com.example.data.mapper.PlayerMapper.toEntity
import com.example.data.mapper.ShotMapper.toDomain
import com.example.data.mapper.ShotMapper.toEntity
import com.example.domain.model.Player
import com.example.domain.model.Shot
import com.example.domain.repository.GolfRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GolfRepositoryImpl(
    private val golfApi: GolfApi,
    private val golfDao: GolfDao,
) : GolfRepository {

    override fun observePlayers(): Flow<List<Player>> =
        golfDao.observePlayers().map { it.toDomain() }

    override fun observeShots(playerId: String): Flow<List<Shot>> =
        golfDao.observeShots(playerId).map { it.toDomain() }

    override suspend fun refreshPLayers() {
        val players = golfApi.getPlayers().map { it.toEntity() }
        golfDao.upsertPlayers(players)
        Timber.d("Refreshed %d players from API", players.size)
    }

    override suspend fun refreshShots() {
        val shots = golfApi.getShots().map { it.toEntity() }
        golfDao.upsertShots(shots)
        Timber.d("Refreshed %d shots from API", shots.size)
    }
}
