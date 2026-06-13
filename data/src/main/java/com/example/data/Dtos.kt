package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "club") val club: String,
    @Json(name = "avgBallSpeed") val avgBallSpeed: Double,
    @Json(name = "imageurl") val imageurl: String,
)

@JsonClass(generateAdapter = true)
data class ShotDto(
    @Json(name = "id") val id: String,
    @Json(name = "playerid") val playerid: String,
    @Json(name = "ball_speed") val ballSpeed: Double,
    @Json(name = "launch_angle") val launchAngle: Double,
    @Json(name = "carry_distance") val carryDistance: Double,
    @Json(name = "clubtype") val clubtype: String,
    @Json(name = "spin_rate") val spinRate: Int,
)
