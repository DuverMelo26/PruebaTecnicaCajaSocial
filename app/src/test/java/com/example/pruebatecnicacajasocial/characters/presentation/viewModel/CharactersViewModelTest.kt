package com.example.pruebatecnicacajasocial.characters.presentation.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pruebatecnicacajasocial.MainCoroutineRule
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.useCase.DeleteCharacterByIdUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.EditCharacterUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.GetMyCharactersUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.GetPaginatedCharactersUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.SaveNewCharacterUseCase
import com.example.pruebatecnicacajasocial.characters.presentation.state.AdjustCharacterStatus
import com.example.pruebatecnicacajasocial.characters.presentation.state.SearchForMyCharactersStatus
import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var charactersViewModel: CharactersViewModel
    private val getPaginatedCharactersUseCase: GetPaginatedCharactersUseCase = mockk()
    private val getMyCharactersUseCase: GetMyCharactersUseCase = mockk()
    private val saveNewCharacterUseCase: SaveNewCharacterUseCase = mockk()
    private val editCharacterUseCase: EditCharacterUseCase = mockk()
    private val deleteCharacterByIdUseCase: DeleteCharacterByIdUseCase = mockk()
    @Before
    fun setup() {
        charactersViewModel = CharactersViewModel(
            getPaginatedCharactersUseCase = getPaginatedCharactersUseCase,
            getMyCharactersUseCase = getMyCharactersUseCase,
            saveNewCharacterUseCase = saveNewCharacterUseCase,
            deleteCharacterByIdUseCase = deleteCharacterByIdUseCase,
            editCharacterUseCase = editCharacterUseCase
        )
    }

    @Test
    fun `getCharacters retorna la primera pagina de personajes exitosamente`() = runTest {
        // Given
        val personajesFake = listOf(
            CharacterDomain(id = 1, name = "nombre1")
        )
        coEvery { getPaginatedCharactersUseCase(any()) } returns ApiResponseStatus.Success(personajesFake)

        // When
        charactersViewModel.getCharacters()

        // Then
        assertTrue(charactersViewModel.getPaginatedCharactersStatus.value is ApiResponseStatus.Success)
        assertEquals(1, charactersViewModel.listApiCharacters.size)
    }

    @Test
    fun `getCharacters retorna error desde el caso de uso para obtener los personajes de la primera pagina`() = runTest {
        // Given
        val idMessageFake = 1
        coEvery { getPaginatedCharactersUseCase(any()) } returns ApiResponseStatus.Error(idMessageFake)

        // When
        charactersViewModel.getCharacters()

        // Then
        assertTrue(charactersViewModel.getPaginatedCharactersStatus.value is ApiResponseStatus.Error)
        assertEquals(idMessageFake, (charactersViewModel.getPaginatedCharactersStatus.value as ApiResponseStatus.Error).message)
    }

    @Test
    fun `getLocalCharacters retorna un listado de personajes de la base de datos local`() = runTest {
        // Given
        val personajesFake = listOf(
            CharacterDomain(id = 1, name = "nombre1")
        )
        coEvery { getMyCharactersUseCase() } returns personajesFake
        // When
        charactersViewModel.getLocalCharacters()

        // Then
        assertTrue(charactersViewModel.getMyCharactersStatus.value is SearchForMyCharactersStatus.CompleteSearch)
        assertEquals(1, charactersViewModel.listMyCharacters.size)
    }

    @Test
    fun `createNewCharacter crea correctamente un nuevo personaje y lo guarda`() = runTest {
        // Given
        val nombreFake = "Test"
        coEvery { saveNewCharacterUseCase(any()) } returns 1

        // When
        charactersViewModel.createNewCharacter(nombreFake)

        // Then
        assertEquals(1, charactersViewModel.listMyCharacters.size)
        assertEquals(nombreFake, charactersViewModel.listMyCharacters[0].name)
        coVerify(exactly = 1) { saveNewCharacterUseCase(any()) }
    }

    @Test
    fun `deleteCharacter borra personaje de base de datos correctamente y se borra de la lista de personajes locales`() = runTest {
        // Given
        coEvery { deleteCharacterByIdUseCase(any()) } returns Unit
        val nombreFake = "Test"
        coEvery { saveNewCharacterUseCase(any()) } returns 1
        charactersViewModel.createNewCharacter(nombreFake)
        // When

        charactersViewModel.deleteCharacter(1)

        // Then
        assertEquals(0, charactersViewModel.listMyCharacters.size)
        coVerify { deleteCharacterByIdUseCase(any()) }
    }

    @Test
    fun `deleteCharacter borra personaje de base de datos correctamente y no se borra de la lista de personajes locales porque no habia ese id`() = runTest {
        // Given
        coEvery { deleteCharacterByIdUseCase(any()) } returns Unit
        val nombreFake = "Test"
        coEvery { saveNewCharacterUseCase(any()) } returns 1
        charactersViewModel.createNewCharacter(nombreFake)
        // When

        charactersViewModel.deleteCharacter(2)

        // Then
        assertEquals(1, charactersViewModel.listMyCharacters.size)
        coVerify { deleteCharacterByIdUseCase(any()) }
    }

    @Test
    fun `editDataCharacter edita en base de datos y en la lista del viewModel un personaje que existe correctamente`() = runTest {
        // Given
        coEvery { editCharacterUseCase(any()) } returns Unit
        val nombreFake = "Test"
        coEvery { saveNewCharacterUseCase(any()) } returns 1
        charactersViewModel.createNewCharacter(nombreFake)
        // When
        charactersViewModel.editDataCharacter(CharacterDomain(id = 1, "NombreEditado"))

        // Then
        assertEquals(AdjustCharacterStatus.Complete, charactersViewModel.editCharacterStatus.value)
        coVerify(exactly = 1) { editCharacterUseCase(any()) }
    }

}