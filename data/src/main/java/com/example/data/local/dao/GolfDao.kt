package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.PlayerEntity
import com.example.data.local.entity.ShotEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GolfDao {

    @Query("SELECT * FROM players")
    fun observePlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM shots WHERE playerid = :playerId")
    fun observeShots(playerId: String): Flow<List<ShotEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlayers(players: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShots(shots: List<ShotEntity>)
}
