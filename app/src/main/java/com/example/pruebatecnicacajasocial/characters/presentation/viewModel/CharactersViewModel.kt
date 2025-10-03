package com.example.pruebatecnicacajasocial.characters.presentation.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicacajasocial.characters.URL_NO_IMAGE
import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.domain.useCase.DeleteCharacterByIdUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.EditCharacterUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.GetMyCharactersUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.GetPaginatedCharactersUseCase
import com.example.pruebatecnicacajasocial.characters.domain.useCase.SaveNewCharacterUseCase
import com.example.pruebatecnicacajasocial.characters.presentation.state.CreateNewCharacterStatus
import com.example.pruebatecnicacajasocial.characters.presentation.state.AdjustCharacterStatus
import com.example.pruebatecnicacajasocial.characters.presentation.state.SearchForMyCharactersStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getPaginatedCharactersUseCase: GetPaginatedCharactersUseCase,
    private val getMyCharactersUseCase: GetMyCharactersUseCase,
    private val saveNewCharacterUseCase: SaveNewCharacterUseCase,
    private val deleteCharacterByIdUseCase: DeleteCharacterByIdUseCase,
    private val editCharacterUseCase: EditCharacterUseCase
) : ViewModel() {
    val listApiCharacters = mutableStateListOf<CharacterDomain>()
    val listApiCharactersBackup = mutableListOf<CharacterDomain>()
    val listCategories = mutableStateListOf<String>()
    private var currentPage: Int = 1
    val getPaginatedCharactersStatus = MutableLiveData<ApiResponseStatus<List<CharacterDomain>>>()
    val getMyCharactersStatus = MutableLiveData<SearchForMyCharactersStatus>()
    val saveNewCharacterStatus = MutableLiveData<CreateNewCharacterStatus>()
    val deleteCharacterStatus = MutableLiveData<AdjustCharacterStatus>()
    val editCharacterStatus = MutableLiveData<AdjustCharacterStatus>()
    val listMyCharacters = mutableStateListOf<CharacterDomain>()
    fun getCharacters() = viewModelScope.launch {
        getPaginatedCharactersStatus.value = ApiResponseStatus.Loading()
        handlePaginatedCharacters(getPaginatedCharactersUseCase(page = currentPage))
    }

    private fun handlePaginatedCharacters(apiResponse: ApiResponseStatus<List<CharacterDomain>>) {
        if (apiResponse is ApiResponseStatus.Success) {
            listApiCharacters.addAll(apiResponse.data)
            listCategories.clear()
            listApiCharactersBackup.addAll(apiResponse.data)
            listCategories.addAll(listApiCharactersBackup.map { it.species }.distinct())
            currentPage++
        }
        getPaginatedCharactersStatus.value = apiResponse
    }

    fun getLocalCharacters() = viewModelScope.launch {
        getMyCharactersStatus.value = SearchForMyCharactersStatus.Loading
        handleMyCharacters(getMyCharactersUseCase())
    }

    private fun handleMyCharacters(data: List<CharacterDomain>) {
        listMyCharacters.clear()
        listMyCharacters.addAll(data)
        getMyCharactersStatus.value = SearchForMyCharactersStatus.CompleteSearch
    }

    fun createNewCharacter(name: String) = viewModelScope.launch {
        saveNewCharacterStatus.value = CreateNewCharacterStatus.Loading
        val newCharacter = CharacterDomain(
            id = 0,
            name = name,
            image = URL_NO_IMAGE
        )
        val id = saveNewCharacterUseCase(newCharacter)
        newCharacter.id = id
        listMyCharacters.add(newCharacter)
        saveNewCharacterStatus.value = CreateNewCharacterStatus.CompleteSave
    }

    fun deleteCharacter(id: Int) = viewModelScope.launch {
        deleteCharacterStatus.value = AdjustCharacterStatus.Loading
        deleteCharacterByIdUseCase(id)
        listMyCharacters.removeIf { it.id == id }
        deleteCharacterStatus.value = AdjustCharacterStatus.Complete
    }

    fun editDataCharacter(character: CharacterDomain) = viewModelScope.launch {
        editCharacterStatus.value = AdjustCharacterStatus.Loading
        editCharacterUseCase(character)
        listMyCharacters.addAll(
            listMyCharacters
                .map { if (it.id == character.id) character else it }
                .toMutableList()
        )
        editCharacterStatus.value = AdjustCharacterStatus.Complete
    }

    fun orderCharactersByCreateDate(order: String) {
        val sortedList = if (order == "Mas antiguos") {
            listApiCharacters.sortedBy { it.created }
        } else {
            listApiCharacters.sortedByDescending { it.created }
        }
        listApiCharacters.clear()
        listApiCharacters.addAll(sortedList)
    }

    fun orderCharactersBySpecie(specie: String?) {
        if (specie != null) {
            val listFilter = listApiCharactersBackup.filter { it.species == specie }
            listApiCharacters.clear()
            listApiCharacters.addAll(listFilter)
            return
        }
        listApiCharacters.clear()
        listApiCharacters.addAll(listApiCharactersBackup)
    }
}