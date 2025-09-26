package com.empresa.kny.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.empresa.kny.domain.charactersDomain.Content

@Composable
fun CharactersScreen(
    viewModel: GetCharactersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val characters: LazyPagingItems<Content> = viewModel.characters.collectAsLazyPagingItems()

    // Función interna para manejar traducciones
    @Composable
    fun translatedText(id: Int?, text: String, type: String): State<TranslationState> {
        val key = "${id ?: -1}_$type"
        LaunchedEffect(key, text) {
            viewModel.translate(context, key, text)
        }
        return viewModel.getTranslationState(key).collectAsState()
    }

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

                    character.description?.let { desc ->
                        when (val state = translatedText(character.id, desc, "description").value) {
                            is TranslationState.Loading -> CircularProgressIndicator()
                            is TranslationState.Success -> Text(text = state.text)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    character.quote?.let { quote ->
                        when (val state = translatedText(character.id, quote, "quote").value) {
                            is TranslationState.Loading -> CircularProgressIndicator()
                            is TranslationState.Success -> Text(text = state.text)
                        }
                    }
                }
            }
        }
    }
}