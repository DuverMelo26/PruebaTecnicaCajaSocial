package com.example.pruebatecnicacajasocial.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pruebatecnicacajasocial.core.database.entities.CharacterEntity

@Dao
interface MyCharactersDao {
    @Query("SELECT * FROM MyCharacters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity) : Long

    @Query("DELETE FROM MyCharacters WHERE id = :id")
    suspend fun deleteCharacterById(id: Int)

    @Query("UPDATE MyCharacters SET name = :name WHERE id = :id")
    suspend fun editCharacter(id: Int, name: String): Int
}