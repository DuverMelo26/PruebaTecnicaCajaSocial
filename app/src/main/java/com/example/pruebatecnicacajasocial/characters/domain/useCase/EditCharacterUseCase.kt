package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import javax.inject.Inject

class EditCharacterUseCase @Inject constructor(
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface
) {
    suspend operator fun invoke(character: CharacterDomain) = charactersLocalRepositoryInterface.editCharacter(character)
}