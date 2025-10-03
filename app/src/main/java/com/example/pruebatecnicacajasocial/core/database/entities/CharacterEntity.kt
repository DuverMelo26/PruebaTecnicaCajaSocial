package com.example.pruebatecnicacajasocial.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain

@Entity(tableName = "MyCharacters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: String
)

fun List<CharacterEntity>.toListCharacterDomain(): List<CharacterDomain> {
    val listCharacter = mutableListOf<CharacterDomain>()
    this.forEach { entity ->
        listCharacter.add(
            entity.toCharacterDomain()
        )
    }
    return listCharacter
}

fun CharacterEntity.toCharacterDomain(): CharacterDomain = CharacterDomain(
    id = id,
    name = name,
    image = image
)