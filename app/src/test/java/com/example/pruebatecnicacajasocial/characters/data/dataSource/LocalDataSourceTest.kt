package com.example.pruebatecnicacajasocial.characters.data.dataSource

import com.example.pruebatecnicacajasocial.core.database.daos.MyCharactersDao
import com.example.pruebatecnicacajasocial.core.database.entities.CharacterEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {
    private lateinit var localDataSource: LocalDataSource
    private val myCharactersDao: MyCharactersDao = mockk()

    @Before
    fun setup() {
        localDataSource = LocalDataSource(myCharactersDao)
    }

    @Test
    fun `getAllCharacters retorna desde el dao de room un listado de CharacterEntity`() = runTest {
        // Given
        coEvery { myCharactersDao.getAllCharacters() } returns listOf(CharacterEntity(
            id = 1,
            name = "Test",
            image = "Url"
        ))

        // When
        val list = localDataSource.getAllCharacters()

        // Then
        coVerify(exactly = 1) { myCharactersDao.getAllCharacters() }
        assert(list.size == 1)
    }

    @Test
    fun `saveNewCharacter retorna desde el dao de room un id de registro`() = runTest {
        // Given
        coEvery { myCharactersDao.insertCharacter(any()) } returns 2L

        // When
        val id = localDataSource.saveNewCharacter(CharacterEntity(
            id = 0,
            name = "Test",
            image = "Url"
        ))

        // Then
        coVerify(exactly = 1) { myCharactersDao.insertCharacter(any()) }
        assert(2L == id)
    }

    @Test
    fun `deleteCharacterById retorna desde el dao de room un valor Unit al borrar un registro`() = runTest {
        // Given
        coEvery { myCharactersDao.deleteCharacterById(any()) } returns Unit

        // When
        localDataSource.deleteCharacterById(2)

        // Then
        coVerify(exactly = 1) { myCharactersDao.deleteCharacterById(any()) }
    }

    @Test
    fun `editCharacter retorna desde el dao de room un valor 1 para confirmar quue se edito un registro`() = runTest {
        // Given
        coEvery { myCharactersDao.editCharacter(any(), any()) } returns 1

        // When
        localDataSource.editCharacter(2, "")

        // Then
        coVerify(exactly = 1) { myCharactersDao.editCharacter(any(), any()) }
    }
}