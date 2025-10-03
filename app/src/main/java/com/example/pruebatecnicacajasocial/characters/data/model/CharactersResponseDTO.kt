package com.example.pruebatecnicacajasocial.characters.data.model

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.google.gson.annotations.SerializedName

data class CharactersResponseDTO(
    @SerializedName("results") val characters: List<CharacterDTO>
)