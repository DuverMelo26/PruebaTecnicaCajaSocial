package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SaveNewCharacterUseCaseTest {
    private lateinit var saveNewCharacterUseCase: SaveNewCharacterUseCase
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface = mockk()

    @Before
    fun setup() {
        saveNewCharacterUseCase = SaveNewCharacterUseCase(charactersLocalRepositoryInterface)
    }

    @Test
    fun `saveNewCharacterUseCase retorna el id de un nuevo registro al guardar un nuevo personaje en bd`() =
        runTest {
            // Given
            val characterFake = CharacterDomain(id = 1, name = "")
            coEvery { saveNewCharacterUseCase(any()) } returns 1

            // When
            val response = saveNewCharacterUseCase(characterFake)

            // Then
            assertEquals(1, response)
        }
}