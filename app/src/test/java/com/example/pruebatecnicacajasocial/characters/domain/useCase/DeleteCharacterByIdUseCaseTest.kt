package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteCharacterByIdUseCaseTest {
    private lateinit var deleteCharacterByIdUseCase: DeleteCharacterByIdUseCase
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface = mockk()

    @Before
    fun setup() {
        deleteCharacterByIdUseCase = DeleteCharacterByIdUseCase(
            charactersLocalRepositoryInterface = charactersLocalRepositoryInterface
        )
    }

    @Test
    fun `deleteCharacterByIdUseCase llama correctamente a la funcion del repositorio y retorna una respuesta Unit`() =
        runTest {
            // Given
            coEvery { charactersLocalRepositoryInterface.deleteCharacterById(any()) } returns Unit

            // When
            val response = deleteCharacterByIdUseCase(1)

            // Then
            assert(response == Unit)
            coVerify(exactly = 1) { charactersLocalRepositoryInterface.deleteCharacterById(any()) }
        }

}