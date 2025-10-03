package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import javax.inject.Inject

class DeleteCharacterByIdUseCase @Inject constructor(
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface
) {
    suspend operator fun invoke(id: Int) = charactersLocalRepositoryInterface.deleteCharacterById(id)
}