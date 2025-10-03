package com.example.pruebatecnicacajasocial.characters.data.dataSource

import com.example.pruebatecnicacajasocial.characters.data.model.CharacterDTO
import com.example.pruebatecnicacajasocial.characters.data.model.CharactersResponseDTO
import com.example.pruebatecnicacajasocial.characters.data.model.LocationCharacterDTO
import com.example.pruebatecnicacajasocial.characters.data.model.OriginCharacterDTO
import com.example.pruebatecnicacajasocial.core.network.RickAndMortyApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ApiRemoteDataSourceTest {
    private lateinit var apiRemoteDataSource: ApiRemoteDataSource
    private val rickAndMortyApi: RickAndMortyApi = mockk()

    @Before
    fun setup() {
        apiRemoteDataSource = ApiRemoteDataSource(rickAndMortyApi)
    }

    @Test
    fun `getPaginatedCharacters retorna desde api un response success con un listado de personajes`() =
        runTest {
            // Given
            val responseFake = Response.success(CharactersResponseDTO(
                characters = listOf(CharacterDTO(
                    id = 1,
                    name = "Test",
                    status = "",
                    species = "",
                    type = "",
                    gender = "",
                    image = "url",
                    origin = OriginCharacterDTO(""),
                    location = LocationCharacterDTO(""),
                    created = ""
                ))
            ))
            coEvery { rickAndMortyApi.getPaginatedCharacters(any()) } returns responseFake

            // When
            val response = apiRemoteDataSource.getPaginatedCharacters(1)

            // Then
            assert(response.isSuccessful)
        }

    @Test
    fun `getPaginatedCharacters retorna desde api un response error`() =
        runTest {
            // Given
            val errorBodyFake = "".toResponseBody("application/json".toMediaTypeOrNull())
            val responseError = Response.error<CharactersResponseDTO>(500, errorBodyFake)
            coEvery { rickAndMortyApi.getPaginatedCharacters(any()) } returns responseError

            // When
            val response = apiRemoteDataSource.getPaginatedCharacters(1)

            // Then
            assert(!response.isSuccessful)
        }
}