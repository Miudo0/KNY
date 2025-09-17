package com.empresa.kny.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CharactersScreen(
    viewModel: GetCharactersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.characters.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCharacters()
    }
    when (state) {
        is CharactersState.Loading -> {
            CircularProgressIndicator()
        }
        is CharactersState.Error -> {
            Text(text = (state as CharactersState.Error).message)
        }
        is CharactersState.Success -> {
            val characters = (state as CharactersState.Success).characters
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(characters) { character ->
                    character.name?.let { Text(text = it) }
                }
            }
        }
    }
}