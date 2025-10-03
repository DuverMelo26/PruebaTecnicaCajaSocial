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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pruebatecnicacajasocial.R
import com.example.pruebatecnicacajasocial.core.network.ApiResponseStatus
import com.example.pruebatecnicacajasocial.core.ui.LoaderBar
import com.example.pruebatecnicacajasocial.characters.domain.model.CharacterDomain
import com.example.pruebatecnicacajasocial.characters.presentation.viewModel.CharactersViewModel

@Composable
fun CharactersScreen(
    charactersViewModel: CharactersViewModel,
    onClickLocalCharacters: () -> Unit
) {
    val searchCharacter = remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val listCharacters = charactersViewModel.listApiCharacters
    val apiStatus = charactersViewModel.getPaginatedCharactersStatus.observeAsState()
    var expandedOrder by remember { mutableStateOf(false) }
    var selectedOrder by remember { mutableStateOf("Seleccionar") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }
            .collect { visibleItems ->
                if (charactersViewModel.listApiCharacters.isEmpty()) {
                    charactersViewModel.getCharacters()
                    return@collect
                }

                if (visibleItems >= listCharacters.size && apiStatus.value !is ApiResponseStatus.Loading) {
                    charactersViewModel.getCharacters()
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            if (listCharacters.isNotEmpty()) {
                CharacterFinder(searchCharacter = searchCharacter)
                OrdenarPorFecha(
                    expandedOrder = {
                        expandedOrder = it
                    },
                    selectedOrder = selectedOrder,
                    expanded = expandedOrder,
                    onSelectedOrder = {
                        selectedOrder = it
                        charactersViewModel.orderCharactersByCreateDate(it)
                    }
                )

                ListaCategorias(
                    selectedCategory = selectedCategory,
                    onSelectedCategory = {
                        selectedCategory = it
                        charactersViewModel.orderCharactersBySpecie(it)
                    },
                    listCategories = charactersViewModel.listCategories
                )
            } else {
                LoaderBar(modifier = Modifier.fillMaxSize())
            }

            ListCharacters(
                listState = listState,
                listCharacters = listCharacters,
                searchCharacter = searchCharacter,
                apiStatus = apiStatus.value
            )
        }

        ButtonLocalCharacters(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            onClickLocalCharacters = onClickLocalCharacters
        )
    }
}

@Composable
fun ListaCategorias(
    selectedCategory: String?,
    onSelectedCategory: (String?) -> Unit,
    listCategories: SnapshotStateList<String>
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Categorias por especie:", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listCategories.size) { index ->
                val category = listCategories[index]
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        onSelectedCategory (if (selectedCategory == category) null else category)
                    },
                    label = { Text(category) }
                )
            }
        }
    }

}

@Composable
fun OrdenarPorFecha(expandedOrder: (Boolean) -> Unit, onSelectedOrder: (String) -> Unit, expanded: Boolean, selectedOrder: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Ordenar:", style = MaterialTheme.typography.bodyMedium)

        Box {
            TextButton(onClick = { expandedOrder(true) }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                contentColor = Color.Black
            )) {
                Text(selectedOrder, fontWeight = FontWeight.Bold)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expandedOrder(false) }
            ) {
                DropdownMenuItem(
                    text = { Text("Mas recientes") },
                    onClick = {
                        onSelectedOrder("Mas recientes")
                        expandedOrder(false)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Mas antiguos") },
                    onClick = {
                        onSelectedOrder("Mas antiguos")
                        expandedOrder(false)
                    }
                )
            }
        }
    }
}

@Composable
private fun ButtonLocalCharacters(
    modifier : Modifier = Modifier,
    onClickLocalCharacters: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = { onClickLocalCharacters() },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Text(text = "Ver mis personajes")
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "My characters",
            tint = Color.White
        )
    }
}

@Composable
private fun ListCharacters(
    listState: LazyListState,
    listCharacters: List<CharacterDomain>,
    searchCharacter: MutableState<String>,
    apiStatus: ApiResponseStatus<List<CharacterDomain>>?
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            listCharacters.filter { it.gender.contains(searchCharacter.value, true) || it.name.contains(searchCharacter.value, true) || it.type.contains(searchCharacter.value, true) }
        ) { character ->
            ItemCharacter(
                character = character,
                onClick = {}
            )
        }

        item {
            when (apiStatus) {
                is ApiResponseStatus.Error -> AlertView(apiStatus.message)
                is ApiResponseStatus.Loading -> LoaderBar(
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                )
                else -> {}
            }
        }
    }
}

@Composable
private fun AlertView(message: Int) {
    Text(
        text = stringResource(id = message),
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ItemCharacter(
    character: CharacterDomain,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(12.dp)
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
        Column (
            Modifier.height(100.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp
            )
            ShowDataText(character.type, title = "Tipo:")
            ShowDataText(character.gender, title = "Genero:")
            ShowDataText(character.status, title = "Estado:")
            ShowDataText(character.species, title = "Especie:")
        }
    }
}

@Composable
private fun ShowDataText(text: String, title: String) {
    if (text.isEmpty()) return
    Text(
        text = "$title $text",
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = 13.sp
    )
}

@Composable
private fun CharacterFinder(
    searchCharacter: MutableState<String>
) {
    OutlinedTextField(
        value = searchCharacter.value,
        onValueChange = { searchCharacter.value = it },
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.buscar_personaje),
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