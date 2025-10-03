package com.example.pruebatecnicacajasocial.characters.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebatecnicacajasocial.R

@Composable
fun CreateNewCharacterDialog(
    onDismiss: () -> Unit,
    onSaveCharacter: (String) -> Unit
) {
    var nameCharacter by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.crear_personaje), color = Color.Black) },
        text = {
            Column {
                Text(stringResource(R.string.ingrese_el_nombre_de_su_nuevo_personaje), color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nameCharacter,
                    onValueChange = { nameCharacter = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nombre", color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedLeadingIconColor = Color.Black,
                        unfocusedLeadingIconColor = Color.Black,
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Black,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSaveCharacter(nameCharacter)
                }
            ) {
                Text("Guardar", color = Color.Black)
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