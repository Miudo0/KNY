package com.empresa.kny.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import androidx.paging.LoadState
import com.empresa.kny.domain.charactersDomain.Content

@Composable
fun CharactersScreen(
    viewModel: GetCharactersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    // Obtener los datos paginados como LazyPagingItems
    val characters: LazyPagingItems<Content> = viewModel.characters.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            if (character != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = character.img,
                        contentDescription = character.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    character.name?.let { Text(text = it) }
                    Spacer(modifier = Modifier.height(8.dp))
                    character.description?.let { Text(text = it) }
                }
            }
        }

        // Mostrar indicador de carga al añadir más páginas
        item {
            if (characters.loadState.append is LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Mostrar error al añadir más páginas
        item {
            val errorState = characters.loadState.append as? LoadState.Error
            errorState?.let {
                Text(
                    text = "Error al cargar más: ${it.error.localizedMessage}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    // Mostrar indicador inicial de carga
    if (characters.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    // Mostrar error inicial
    if (characters.loadState.refresh is LoadState.Error) {
        val errorState = characters.loadState.refresh as LoadState.Error
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(text = "Error al cargar: ${errorState.error.localizedMessage}")
        }
    }
}