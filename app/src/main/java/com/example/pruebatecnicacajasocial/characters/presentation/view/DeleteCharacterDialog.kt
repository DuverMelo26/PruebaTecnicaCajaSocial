package com.example.pruebatecnicacajasocial.characters.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.pruebatecnicacajasocial.R

@Composable
fun DeleteCharacterDialog(
    onDismiss: () -> Unit,
    onDeleteCharacter: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.borrar_personaje), color = Color.Black) },
        text = {
            Text(
                stringResource(R.string.quiere_borrar_este_personaje),
                color = Color.Black,
                fontSize = 14.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteCharacter()
                }
            ) {
                Text("Borrar", color = Color.Black)
            }
        },
        containerColor = Color.White,
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancelar", color = Color.Black)
            }
        }
    )
}