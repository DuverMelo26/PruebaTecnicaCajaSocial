package com.example.pruebatecnicacajasocial.characters.data.model

import com.example.pruebatecnicacajasocial.characters.domain.model.OriginCharacterDomain
import com.google.gson.annotations.SerializedName

data class OriginCharacterDTO(
    @SerializedName("name") val name: String
)

fun OriginCharacterDTO.toOriginCharacterDomain() : OriginCharacterDomain =
    OriginCharacterDomain(
        name = name
    )