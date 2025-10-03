package com.example.pruebatecnicacajasocial.characters.presentation.state

sealed class AdjustCharacterStatus {
    data object Loading: AdjustCharacterStatus()
    data object Complete : AdjustCharacterStatus()
}