package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersRemoteRepositoryInterface
import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPaginatedCharactersUseCaseTest {
    private lateinit var getPaginatedCharactersUseCase: GetPaginatedCharactersUseCase
    private val charactersRemoteRepositoryInterface: CharactersRemoteRepositoryInterface = mockk()

    @Before
    fun setup() {
        getPaginatedCharactersUseCase = GetPaginatedCharactersUseCase(charactersRemoteRepositoryInterface)
    }

    @Test
    fun `getPaginatedCharactersUseCase retorna ApiResponse status Success con un listado de personajes`() = runTest {
        // Given
        val fakeResponse = ApiResponseStatus.Success(listOf(CharacterDomain(id = 1, name = "Test")))
        coEvery { getPaginatedCharactersUseCase(any()) } returns fakeResponse

        // When
        val response = getPaginatedCharactersUseCase(1)

        // Then
        assertEquals(response, fakeResponse)
    }
}