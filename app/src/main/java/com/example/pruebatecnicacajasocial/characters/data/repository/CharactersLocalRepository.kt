package com.example.pruebatecnicacajasocial.characters.data.repository

import com.example.pruebatecnicacajasocial.core.database.entities.toListCharacterDomain
import com.example.pruebatecnicacajasocial.characters.data.dataSource.LocalDataSource
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.model.toCharacterEntity
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersLocalRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : CharactersLocalRepositoryInterface {
    override suspend fun getMyCharacters(): List<CharacterDomain> = withContext(Dispatchers.IO) {
        val myCharacters = localDataSource.getAllCharacters()
        if (myCharacters.isEmpty()) return@withContext listOf()
        myCharacters.toListCharacterDomain()
    }

    override suspend fun saveNewCharacter(character: CharacterDomain): Int = withContext(Dispatchers.IO) {
        val characterEntity = character.toCharacterEntity()
        localDataSource.saveNewCharacter(characterEntity).toInt()
    }

    override suspend fun deleteCharacterById(id: Int) = withContext(Dispatchers.IO) {
        localDataSource.deleteCharacterById(id)
    }

    override suspend fun editCharacter(character: CharacterDomain) : Unit = withContext(Dispatchers.IO) {
        localDataSource.editCharacter(id = character.id, name = character.name)
    }
}