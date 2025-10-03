package com.example.pruebatecnicacajasocial.characters.presentation.state

sealed class SearchForMyCharactersStatus {
    data object Loading: SearchForMyCharactersStatus()
    data object CompleteSearch : SearchForMyCharactersStatus()
}