package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val club: String,
    val avgBallSpeed: Double,
    val imageurl: String,
)

@Entity(tableName = "shots")
data class ShotEntity(
    @PrimaryKey val id: String,
    val playerid: String,
    val ballSpeed: Double,
    val launchAngle: Double,
    val carryDistance: Double,
    val clubtype: String,
    val spinRate: Int,
)
