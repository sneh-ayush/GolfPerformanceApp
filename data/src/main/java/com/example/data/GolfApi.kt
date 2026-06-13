package com.example.data

import retrofit2.http.GET

interface GolfApi {

    @GET("Players.json")
    suspend fun getPlayers(): List<PlayerDto>

    @GET("shots.json")
    suspend fun getShots(): List<ShotDto>
}
