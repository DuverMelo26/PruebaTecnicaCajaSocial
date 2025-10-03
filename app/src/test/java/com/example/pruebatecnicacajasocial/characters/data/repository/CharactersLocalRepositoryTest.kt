package com.example.pruebatecnicacajasocial.characters.data.repository

import com.example.pruebatecnicacajasocial.characters.data.dataSource.LocalDataSource
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.core.database.entities.CharacterEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CharactersLocalRepositoryTest {
    private lateinit var charactersLocalRepository: CharactersLocalRepository
    private val localDataSource: LocalDataSource = mockk()

    @Before
    fun setup() {
        charactersLocalRepository = CharactersLocalRepository(localDataSource)
    }

    @Test
    fun `editCharacter retorna desde bd un valor Unit al editar un personaje`() = runTest {
        // Given
        val characterFake = CharacterDomain(id = 1, name = "")
        coEvery { localDataSource.editCharacter(any(), any()) } returns 1

        // When
        charactersLocalRepository.editCharacter(characterFake)

        // Then
        coVerify(exactly = 1) { localDataSource.editCharacter(any(), any()) }
    }

    @Test
    fun `deleteCharacterById retorna desde bd un valor Unit al borrar un personaje`() = runTest {
        // Given
        coEvery { localDataSource.deleteCharacterById(any()) } returns Unit

        // When
        charactersLocalRepository.deleteCharacterById(1)

        // Then
        coVerify(exactly = 1) { localDataSource.deleteCharacterById(any()) }
    }

    @Test
    fun `saveNewCharacter retorna desde bd el id de registro al guardar un personaje`() = runTest {
        // Given
        coEvery { localDataSource.saveNewCharacter(any()) } returns 1L

        // When
        val id = charactersLocalRepository.saveNewCharacter(CharacterDomain(id = 1, name = ""))

        // Then
        coVerify(exactly = 1) { localDataSource.saveNewCharacter(any()) }
        assert(id == 1)
    }

    @Test
    fun `getMyCharacters retorna desde bd un listado vacio de personajes`() = runTest {
        // Given
        coEvery { localDataSource.getAllCharacters() } returns listOf()

        // When
        val list = charactersLocalRepository.getMyCharacters()

        // Then
        coVerify(exactly = 1) { localDataSource.getAllCharacters() }
        assert(list.isEmpty())
    }

    @Test
    fun `getMyCharacters retorna desde bd un listado de personajes`() = runTest {
        // Given
        coEvery { localDataSource.getAllCharacters() } returns listOf(CharacterEntity(id = 1, name = "Test", image = ""))

        // When
        val list = charactersLocalRepository.getMyCharacters()

        // Then
        coVerify(exactly = 1) { localDataSource.getAllCharacters() }
        assert(list.size == 1)
    }

}