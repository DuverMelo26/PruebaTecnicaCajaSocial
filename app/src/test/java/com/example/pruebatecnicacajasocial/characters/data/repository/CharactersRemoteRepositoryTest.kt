package com.example.pruebatecnicacajasocial.characters.data.repository

import com.example.pruebatecnicacajasocial.characters.data.dataSource.ApiRemoteDataSource
import com.example.pruebatecnicacajasocial.characters.data.model.CharacterDTO
import com.example.pruebatecnicacajasocial.characters.data.model.CharactersResponseDTO
import com.example.pruebatecnicacajasocial.characters.data.model.LocationCharacterDTO
import com.example.pruebatecnicacajasocial.characters.data.model.OriginCharacterDTO
import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CharactersRemoteRepositoryTest {

    private lateinit var charactersRemoteRepository: CharactersRemoteRepository
    private val apiRemoteDataSource: ApiRemoteDataSource = mockk()

    @Before
    fun setup() {
        charactersRemoteRepository = CharactersRemoteRepository(apiRemoteDataSource)
    }

    @Test
    fun `getPaginatedCharacters retorna desde el datasource un response success desde api`() =
        runTest {
            // Given
            val bodyFake = CharactersResponseDTO(
                characters = listOf(CharacterDTO(
                    id = 1,
                    name = "Test",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "",
                    image = "",
                    origin = OriginCharacterDTO(""),
                    location = LocationCharacterDTO(""),
                    created = ""
                ))
            )
            val responseSuccess = Response.success(bodyFake)
            coEvery { apiRemoteDataSource.getPaginatedCharacters(any()) } returns responseSuccess

            // When
            val responseRepository = charactersRemoteRepository.getPaginatedCharacters(1)

            // Then
            assert(responseRepository is ApiResponseStatus.Success)
        }

    @Test
    fun `getPaginatedCharacters retorna desde el datasource un response error desde api y se retorna un ApiResponseStatus Error`() =
        runTest {
            // Given
            val errorBodyFake = "".toResponseBody("application/json".toMediaTypeOrNull())
            val responseError = Response.error<CharactersResponseDTO>(500, errorBodyFake)
            coEvery { apiRemoteDataSource.getPaginatedCharacters(any()) } returns responseError

            // When
            val responseRepository = charactersRemoteRepository.getPaginatedCharacters(1)

            // Then
            assert(responseRepository is ApiResponseStatus.Error)
        }

    @Test
    fun `getPaginatedCharacters retorna desde el datasource un response success desde api con body nulo y se retorna un ApiResponseStatus Error`() =
        runTest {
            // Given
            val responseSuccess = Response.success<CharactersResponseDTO>(null)
            coEvery { apiRemoteDataSource.getPaginatedCharacters(any()) } returns responseSuccess

            // When
            val responseRepository = charactersRemoteRepository.getPaginatedCharacters(1)

            // Then
            assert(responseRepository is ApiResponseStatus.Error)
        }

    @Test
    fun `getPaginatedCharacters retorna desde el datasource un response que causa un excepcion y se retorna un ApiResponseStatus Error`() =
        runTest {
            // Given
            coEvery { apiRemoteDataSource.getPaginatedCharacters(any()) } throws RuntimeException("Error de red")

            // When
            val responseRepository = charactersRemoteRepository.getPaginatedCharacters(1)

            // Then
            assert(responseRepository is ApiResponseStatus.Error)
        }

}