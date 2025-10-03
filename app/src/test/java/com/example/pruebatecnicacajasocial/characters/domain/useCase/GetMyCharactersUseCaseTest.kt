package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMyCharactersUseCaseTest {
    private lateinit var getMyCharactersUseCase: GetMyCharactersUseCase
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface = mockk()

    @Before
    fun setup() {
        getMyCharactersUseCase = GetMyCharactersUseCase(charactersLocalRepositoryInterface)
    }

    @Test
    fun `getMyCharactersUseCase retorna un listado de personajes desde bd local`() =
        runTest {
            // Given
            val fakeResponse = listOf(CharacterDomain(id = 1, name = "Nombre"))
            coEvery { getMyCharactersUseCase() } returns fakeResponse

            // When
            val list = getMyCharactersUseCase()

            // Then
            assertEquals(fakeResponse, list)
        }
}