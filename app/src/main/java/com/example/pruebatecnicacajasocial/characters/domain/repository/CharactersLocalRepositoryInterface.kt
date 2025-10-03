package com.example.pruebatecnicacajasocial.characters.domain.repository

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain

interface CharactersLocalRepositoryInterface {
    suspend fun getMyCharacters() : List<CharacterDomain>
    suspend fun saveNewCharacter(character: CharacterDomain): Int
    suspend fun deleteCharacterById(id: Int)
    suspend fun editCharacter(character: CharacterDomain)
}