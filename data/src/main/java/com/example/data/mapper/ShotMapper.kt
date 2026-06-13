package com.example.data.mapper

import com.example.data.ShotDto
import com.example.data.local.entity.ShotEntity
import com.example.domain.model.Shot

object ShotMapper {

    fun ShotDto.toEntity(): ShotEntity = ShotEntity(
        id = id,
        playerid = playerid,
        ballSpeed = ballSpeed,
        launchAngle = launchAngle,
        carryDistance = carryDistance,
        clubtype = clubtype,
        spinRate = spinRate,
    )

    fun ShotEntity.toDomain(): Shot = Shot(
        id = id.toLong(),
        playerId = playerid.toLong(),
        ballSpeed = ballSpeed,
        launchAngle = launchAngle,
        carryDistance = carryDistance,
        clubType = clubtype,
        spinRate = spinRate,
    )

    fun List<ShotEntity>.toDomain(): List<Shot> = map { it.toDomain() }
}
