package com.example.pruebatecnicacajasocial.core.network

import com.example.pruebatecnicacajasocial.characters.data.model.CharactersResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getPaginatedCharacters(
        @Query("page") page: Int
    ) : Response<CharactersResponseDTO>
}