package com.example.pruebatecnicacajasocial.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebatecnicacajasocial.core.database.daos.MyCharactersDao
import com.example.pruebatecnicacajasocial.core.database.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun myCharacterDao(): MyCharactersDao
}