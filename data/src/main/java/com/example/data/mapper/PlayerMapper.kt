package com.example.data.mapper

import com.example.data.PlayerDto
import com.example.data.local.entity.PlayerEntity
import com.example.domain.model.Player

object PlayerMapper {

    fun PlayerDto.toEntity(): PlayerEntity = PlayerEntity(
        id = id,
        name = name,
        club = club,
        avgBallSpeed = avgBallSpeed,
        imageurl = imageurl,
    )

    fun PlayerEntity.toDomain(): Player = Player(
        id = id.toLong(),
        name = name,
        club = club,
        avgBallSpeed = avgBallSpeed,
        imageUrl = imageurl,
    )

    fun List<PlayerEntity>.toDomain(): List<Player> = map { it.toDomain() }
}
