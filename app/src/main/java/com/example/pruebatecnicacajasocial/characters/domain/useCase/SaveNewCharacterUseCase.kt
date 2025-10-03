package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import javax.inject.Inject

class SaveNewCharacterUseCase @Inject constructor(
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface
) {
    suspend operator fun invoke(character: CharacterDomain) : Int =
        charactersLocalRepositoryInterface.saveNewCharacter(character)
}