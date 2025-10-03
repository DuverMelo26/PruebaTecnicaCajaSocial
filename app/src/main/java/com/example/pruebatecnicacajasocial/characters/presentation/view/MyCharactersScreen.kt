package com.example.pruebatecnicacajasocial.characters.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pruebatecnicacajasocial.R
import com.example.pruebatecnicacajasocial.core.ui.LoaderBar
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.presentation.state.SearchForMyCharactersStatus
import com.example.pruebatecnicacajasocial.characters.presentation.viewModel.CharactersViewModel

@Composable
fun MyCharactersScreen(
    charactersViewModel: CharactersViewModel,
    onClickCharacters: () -> Unit,
    createNewCharacter: (String) -> Unit,
    deleteCharacter: (Int) -> Unit,
    editNameCharacter: (CharacterDomain) -> Unit
) {
    val searchCharacter = remember { mutableStateOf("") }
    val editCharacter = remember { mutableStateOf(CharacterDomain(id = 0, name = "")) }
    val idCharacterDelete = remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
    val listCharacters = charactersViewModel.listMyCharacters
    val apiStatus = charactersViewModel.getMyCharactersStatus.observeAsState()
    var showDialogNewCharacter by remember { mutableStateOf(false) }
    var showDialogEditCharacter by remember { mutableStateOf(false) }
    var showDialogDeleteCharacter by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }
            .collect { visibleItems ->
                if (listCharacters.isEmpty()) {
                    charactersViewModel.getLocalCharacters()
                    return@collect
                }

                if (visibleItems >= listCharacters.size && apiStatus.value !is SearchForMyCharactersStatus.Loading) {
                    charactersViewModel.getLocalCharacters()
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (listCharacters.isNotEmpty()) {
                CharacterFinder(searchCharacter = searchCharacter)
            } else {
                when (apiStatus) {
                    is SearchForMyCharactersStatus.Loading -> {
                        LoaderBar(modifier = Modifier.fillMaxSize())
                    }
                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.no_hay_personajes_creados))
                        }
                    }
                }
            }

            ListCharacters(
                listState = listState,
                listCharacters = listCharacters,
                searchCharacter = searchCharacter,
                apiStatus = apiStatus.value,
                deleteCharacter = {
                    idCharacterDelete.value = it
                    showDialogDeleteCharacter = true
                },
                editCharacter = {
                    editCharacter.value = it
                    showDialogEditCharacter = true
                }
            )
        }

        ButtonCharacters(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClickCharacters = onClickCharacters
        )

        NewCharacterButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            onClick = {
                showDialogNewCharacter = true
            }
        )
    }

    if (showDialogNewCharacter) {
        CreateNewCharacterDialog(
            onDismiss = { showDialogNewCharacter = false },
            onSaveCharacter = { name ->
                createNewCharacter(name)
                showDialogNewCharacter = false
            }
        )
    }

    if (showDialogEditCharacter) {
        EditCharacterDialog(
            onDismiss = { showDialogEditCharacter = false },
            onSaveCharacter = { name ->
                editCharacter.value.name = name
                editNameCharacter(editCharacter.value)
                showDialogEditCharacter = false
            },
            name = editCharacter.value.name
        )
    }

    if (showDialogDeleteCharacter) {
        DeleteCharacterDialog(
            onDismiss = { showDialogDeleteCharacter = false },
            onDeleteCharacter = {
                deleteCharacter(idCharacterDelete.value)
                showDialogDeleteCharacter = false
            }
        )
    }
}

@Composable
private fun ButtonCharacters(
    modifier: Modifier = Modifier,
    onClickCharacters: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClickCharacters() },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Text(text = "Ver personajes reales")
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "My characters",
            tint = Color.White
        )
    }
}

@Composable
private fun NewCharacterButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        containerColor = Color.Blue
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Character",
            tint = Color.White
        )
    }
}

@Composable
private fun CharacterFinder(
    searchCharacter: MutableState<String>
) {
    OutlinedTextField(
        value = searchCharacter.value,
        onValueChange = { searchCharacter.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.escribe_el_nombre_de_tu_personaje),
                color = Color.Black
            )
        },
        leadingIcon = {
            Icon(Icons.Rounded.Search, contentDescription = "Buscar personaje", tint = Color.Black)
        },
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

@Composable
private fun ListCharacters(
    listState: LazyListState,
    listCharacters: List<CharacterDomain>,
    searchCharacter: MutableState<String>,
    apiStatus: SearchForMyCharactersStatus?,
    deleteCharacter: (Int) -> Unit,
    editCharacter: (CharacterDomain) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            listCharacters.filter { it.name.contains(searchCharacter.value, true) }
        ) { character ->
            ItemCharacter(
                character = character,
                onClick = {},
                onEdit = {editCharacter(it)},
                onDelete = {deleteCharacter(it)}
            )
        }

        item {
            when (apiStatus) {
                is SearchForMyCharactersStatus.Loading -> LoaderBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )

                else -> {}
            }
        }
    }
}

@Composable
fun ItemCharacter(
    character: CharacterDomain,
    onClick: () -> Unit,
    onEdit: (CharacterDomain) -> Unit,
    onDelete: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(character.image),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            Modifier.height(100.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onEdit(character) }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Editar")
                }

                Button(
                    onClick = { onDelete(character.id) }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Eliminar")
                }
            }
        }
    }
}