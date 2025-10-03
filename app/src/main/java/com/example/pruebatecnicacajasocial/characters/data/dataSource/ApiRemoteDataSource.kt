package com.example.pruebatecnicacajasocial.characters.data.dataSource

import com.example.pruebatecnicacajasocial.core.network.RickAndMortyApi
import com.example.pruebatecnicacajasocial.characters.data.model.CharactersResponseDTO
import retrofit2.Response
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {
    suspend fun getPaginatedCharacters(page: Int) : Response<CharactersResponseDTO> =
        rickAndMortyApi.getPaginatedCharacters(page = page)
}