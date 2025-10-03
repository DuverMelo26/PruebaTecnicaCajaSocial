package com.example.pruebatecnicacajasocial.characters.data.model

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.core.getDateFormat
import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
    @SerializedName("created") val created: String,
    @SerializedName("origin") val origin: OriginCharacterDTO,
    @SerializedName("location") val location: LocationCharacterDTO
)

fun CharacterDTO.toCharacterDomain() : CharacterDomain = CharacterDomain(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    origin = origin.toOriginCharacterDomain(),
    location = location.toLocationCharacterDomain(),
    created = getDateFormat(created)
)

fun List<CharacterDTO>.toListCharacterDomain() : List<CharacterDomain> {
    val listCharacter = mutableListOf<CharacterDomain>()
    this.forEach { characterDTO ->
        listCharacter.add(
            characterDTO.toCharacterDomain()
        )
    }
    return listCharacter
}
