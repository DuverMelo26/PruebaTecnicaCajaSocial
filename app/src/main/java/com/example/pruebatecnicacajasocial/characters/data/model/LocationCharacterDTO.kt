package com.example.pruebatecnicacajasocial.characters.data.model

import com.example.pruebatecnicacajasocial.characters.domain.model.LocationCharacterDomain
import com.google.gson.annotations.SerializedName

data class LocationCharacterDTO(
    @SerializedName("name") val name: String
)

fun LocationCharacterDTO.toLocationCharacterDomain() : LocationCharacterDomain =
    LocationCharacterDomain(
        name = name
    )