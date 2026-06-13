package com.example.domain.model

data class Shot(
    val id: Long,
    val playerId: Long,
    val ballSpeed: Double,
    val launchAngle: Double,
    val carryDistance: Double,
    val clubType: String,
    val spinRate: Int,
)
