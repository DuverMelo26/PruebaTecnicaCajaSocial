package com.example.pruebatecnicacajasocial.characters.domain.repository

import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain

interface CharactersRemoteRepositoryInterface {
    suspend fun getPaginatedCharacters(page: Int) : ApiResponseStatus<List<CharacterDomain>>
}