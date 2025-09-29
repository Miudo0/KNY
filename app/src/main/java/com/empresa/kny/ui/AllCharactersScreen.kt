package com.empresa.kny.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterSummary
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllCharactersScreen(
    modifier: Modifier,
    onCharacterClick: (String, String?) -> Unit,
    viewModel: GetAllCharactersViewModel = hiltViewModel()
) {
    val characters by viewModel.characters.collectAsState()
    val error by viewModel.error.collectAsState()

    val infinite = rememberInfiniteTransition(label = "bg")
    val shift by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgShift"
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Demon Slayer - Personajes", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary) },
                actions = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "search", tint = MaterialTheme.colorScheme.onPrimary)
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface
                        ),
                        start = androidx.compose.ui.geometry.Offset(shift, 0f),
                        end = androidx.compose.ui.geometry.Offset(0f, shift)
                    )
                )
        ) {
            when {
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                            Text(text = error ?: "")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(onClick = { /* expose retry if needed */ }) { Text("Reintentar") }
                        }
                    }
                }
                characters.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        val context = androidx.compose.ui.platform.LocalContext.current
                        val decoderFactory = if (android.os.Build.VERSION.SDK_INT >= 28) {
                            coil.decode.ImageDecoderDecoder.Factory()
                        } else {
                            coil.decode.GifDecoder.Factory()
                        }
                        val request = coil.request.ImageRequest.Builder(context)
                            .data(com.empresa.kny.R.drawable.loader_demonslayer)
                            .decoderFactory(decoderFactory)
                            .build()
                        coil.compose.AsyncImage(
                            model = request,
                            contentDescription = "Cargando",
                            modifier = Modifier.size(140.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(characters) { character ->
                            CharacterItem(character = character, onClick = {
                                character.name?.let { onCharacterClick(it, character.image) }
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: CharacterSummary, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (pressed) 0.97f else 1f, label = "pressScale")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                onClick = { onClick() },
                onClickLabel = character.name ?: "open"
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier.size(84.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.alignByBaseline()) {
                Text(
                    text = character.name ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
