package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersRemoteRepositoryInterface
import javax.inject.Inject

class GetPaginatedCharactersUseCase @Inject constructor(
    private val charactersRemoteRepositoryInterface: CharactersRemoteRepositoryInterface
) {
    suspend operator fun invoke(page: Int) : ApiResponseStatus<List<CharacterDomain>> =
        charactersRemoteRepositoryInterface.getPaginatedCharacters(page = page)
}