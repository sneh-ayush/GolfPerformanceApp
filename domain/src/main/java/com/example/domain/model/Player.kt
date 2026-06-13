package com.example.domain.model

data class Player(
    val id: Long,
    val name: String,
    val club: String,
    val avgBallSpeed: Double,
    val imageUrl: String,
)
