package com.example.pruebatecnicacajasocial.characters.domain.model

import com.example.pruebatecnicacajasocial.core.database.entities.CharacterEntity
import java.util.Date

data class CharacterDomain(
    var id: Int,
    var name: String,
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val image: String = "",
    val created: Date = Date(),
    val origin: OriginCharacterDomain = OriginCharacterDomain(name = ""),
    val location: LocationCharacterDomain = LocationCharacterDomain(name = "")
)

fun CharacterDomain.toCharacterEntity() : CharacterEntity = CharacterEntity(
    name = name,
    image = image
)