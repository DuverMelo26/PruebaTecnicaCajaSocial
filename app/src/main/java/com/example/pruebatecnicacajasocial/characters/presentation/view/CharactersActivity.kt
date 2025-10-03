package com.example.pruebatecnicacajasocial.characters.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebatecnicacajasocial.characters.CHARACTERS_SCREEN_NAME
import com.example.pruebatecnicacajasocial.characters.MY_CHARACTERS_SCREEN_NAME
import com.example.pruebatecnicacajasocial.characters.presentation.view.ui.theme.PruebaTecnicaCajaSocialTheme
import com.example.pruebatecnicacajasocial.characters.presentation.viewModel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersActivity : ComponentActivity() {

    private val charactersViewModel : CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaTecnicaCajaSocialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = CHARACTERS_SCREEN_NAME) {
                    composable(CHARACTERS_SCREEN_NAME) {
                        CharactersScreen(
                            charactersViewModel = charactersViewModel,
                            onClickLocalCharacters = {
                                navController.navigate(MY_CHARACTERS_SCREEN_NAME)
                            }
                        )
                    }

                    composable(MY_CHARACTERS_SCREEN_NAME) {
                        MyCharactersScreen(
                            charactersViewModel = charactersViewModel,
                            onClickCharacters = {
                                navController.navigate(CHARACTERS_SCREEN_NAME)
                            },
                            createNewCharacter = {
                                charactersViewModel.createNewCharacter(it)
                            },
                            deleteCharacter = {
                                charactersViewModel.deleteCharacter(it)
                            },
                            editNameCharacter = {
                                charactersViewModel.editDataCharacter(it)
                            }
                        )
                    }
                }
            }
        }
    }
}