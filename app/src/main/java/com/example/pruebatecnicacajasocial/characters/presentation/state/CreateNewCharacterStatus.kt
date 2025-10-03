package com.example.pruebatecnicacajasocial.characters.presentation.state

sealed class CreateNewCharacterStatus {
    data object Loading: CreateNewCharacterStatus()
    data object CompleteSave : CreateNewCharacterStatus()
}