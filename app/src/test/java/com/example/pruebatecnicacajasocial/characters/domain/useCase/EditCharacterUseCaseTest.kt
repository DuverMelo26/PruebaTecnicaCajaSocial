package com.example.pruebatecnicacajasocial.characters.domain.useCase

import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersLocalRepositoryInterface
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EditCharacterUseCaseTest {
    private lateinit var editCharacterUseCase: EditCharacterUseCase
    private val charactersLocalRepositoryInterface: CharactersLocalRepositoryInterface = mockk()

    @Before
    fun setup() {
        editCharacterUseCase = EditCharacterUseCase(charactersLocalRepositoryInterface)
    }

    @Test
    fun `editCharacterUseCase llama correctamente a la funcion del repositorio y retorna una respuesta Unit`() =
        runTest {
            // Given
            val characterFake = CharacterDomain(id = 1, name = "")
            coEvery { charactersLocalRepositoryInterface.editCharacter(any()) } returns Unit

            // When
            val response = editCharacterUseCase(characterFake)

            // Then
            assert(response == Unit)
            coVerify(exactly = 1) { charactersLocalRepositoryInterface.editCharacter(any()) }
        }

}