package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.GolfDao
import com.example.data.local.entity.PlayerEntity
import com.example.data.local.entity.ShotEntity

@Database(
    entities = [
        PlayerEntity::class,
        ShotEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class GolfDatabase : RoomDatabase() {

    abstract fun golfDao(): GolfDao

    companion object {
        const val DATABASE_NAME = "golf.db"
    }
}
