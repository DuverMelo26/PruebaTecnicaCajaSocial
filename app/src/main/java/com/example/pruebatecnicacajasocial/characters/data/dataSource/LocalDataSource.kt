package com.example.pruebatecnicacajasocial.characters.data.dataSource

import com.example.pruebatecnicacajasocial.core.database.daos.MyCharactersDao
import com.example.pruebatecnicacajasocial.core.database.entities.CharacterEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val myCharactersDao: MyCharactersDao
) {
    suspend fun getAllCharacters() : List<CharacterEntity> = myCharactersDao.getAllCharacters()

    suspend fun saveNewCharacter(character: CharacterEntity) : Long = myCharactersDao.insertCharacter(character)

    suspend fun deleteCharacterById(id: Int) = myCharactersDao.deleteCharacterById(id)

    suspend fun editCharacter(id: Int, name: String) = myCharactersDao.editCharacter(id = id, name = name)
}