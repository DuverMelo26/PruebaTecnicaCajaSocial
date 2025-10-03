package com.example.pruebatecnicacajasocial.characters.data.repository

import com.example.pruebatecnicacajasocial.R
import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import com.example.pruebatecnicacajasocial.characters.data.dataSource.ApiRemoteDataSource
import com.example.pruebatecnicacajasocial.characters.data.model.toListCharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.repository.CharactersRemoteRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRemoteRepository @Inject constructor(
    private val apiRemoteDataSource: ApiRemoteDataSource
) : CharactersRemoteRepositoryInterface {
    override suspend fun getPaginatedCharacters(page: Int): ApiResponseStatus<List<CharacterDomain>> = withContext(Dispatchers.IO) {
        try {
            val call = apiRemoteDataSource.getPaginatedCharacters(page = page)
            if (!call.isSuccessful) return@withContext ApiResponseStatus.Error(R.string.falla_consultando_personajes)

            return@withContext call.body()?.let {
                ApiResponseStatus.Success(it.characters.toListCharacterDomain())
            } ?: ApiResponseStatus.Error(R.string.falla_consultando_personajes)

        } catch (e: Exception) {
            ApiResponseStatus.Error(R.string.falla_consultando_personajes)
        }
    }
}