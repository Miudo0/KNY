package com.empresa.kny.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    name: String,
    initialImageUrl: String? = null,
    onBack: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val detail by viewModel.detail.collectAsState()
    var headerLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(name) {
        viewModel.load(name)
        headerLoaded = true
    }

    val scrollState = rememberScrollState()
    val images: List<String> = remember(detail, initialImageUrl) {
        val gallery = detail?.gallery?.filterNotNull().orEmpty()
        val main = detail?.image
        val base = when {
            gallery.isNotEmpty() && main != null -> listOf(main) + gallery
            gallery.isNotEmpty() -> gallery
            main != null -> listOf(main)
            else -> emptyList()
        }
        if (base.isEmpty() && initialImageUrl != null) listOf(initialImageUrl) else base.ifEmpty { initialImageUrl?.let { listOf(it) } ?: emptyList() }
    }
    var currentImageIndex by remember(images) { mutableStateOf(0) }
    LaunchedEffect(images) {
        if (images.size > 1) {
            while (true) {
                delay(5000)
                currentImageIndex = (currentImageIndex + 1) % images.size
            }
        }
    }
    val parallaxScale by animateFloatAsState(
        targetValue = if (headerLoaded) 1f else 1.05f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "parallaxScale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(detail?.name ?: name, color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back", tint = MaterialTheme.colorScheme.onPrimary)
                    }
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
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(inner)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .aspectRatio(3f / 2f)
                    .clipToBounds()
            ) {
                androidx.compose.animation.Crossfade(
                    targetState = images.getOrNull(currentImageIndex),
                    animationSpec = tween(600, easing = FastOutSlowInEasing),
                    label = "headerCrossfade"
                ) { url ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .crossfade(true)
                            .build(),
                        contentDescription = detail?.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(parallaxScale)
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background)
                            )
                        )
                )
                androidx.compose.animation.AnimatedVisibility(
                    visible = detail?.name != null,
                    enter = fadeIn(animationSpec = tween(500)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    Text(
                        text = detail?.name ?: "",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Gallery carousel
            val gallery = detail?.gallery ?: emptyList()
            if (gallery.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(gallery) { url ->
                        Surface(
                            tonalElevation = 3.dp,
                            shadowElevation = 6.dp,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = detail?.name,
                                modifier = Modifier
                                    .size(96.dp)
                                    .padding(2.dp)
                                    .clickable {
                                        val idx = images.indexOf(url)
                                        if (idx >= 0) currentImageIndex = idx
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            FactsCard(
                left = listOfNotNull(
                    detail?.race?.let { "Raza" to it },
                    detail?.gender?.let { "Género" to it },
                    detail?.age?.let { "Edad" to it }
                ),
                right = listOfNotNull(
                    detail?.affiliation?.let { "Afiliación" to it },
                    detail?.occupation?.let { "Ocupación" to it },
                    detail?.status?.let { "Estado" to it }
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(label = "Altura", value = detail?.height)
            DetailRow(label = "Peso", value = detail?.weight)
            DetailRow(label = "Cumpleaños", value = detail?.birthday)
            DetailRow(label = "Cabello", value = detail?.hairColor)
            DetailRow(label = "Ojos", value = detail?.eyeColor)
            DetailRow(label = "Estilo de combate", value = detail?.combatStyle)
            DetailRow(label = "Pareja(s)", value = detail?.partners)
            DetailRow(label = "Estado", value = detail?.status)
            DetailRow(label = "Familiares", value = detail?.relatives)
            DetailRow(label = "Debut manga", value = detail?.mangaDebut)
            DetailRow(label = "Debut anime", value = detail?.animeDebut)
            DetailRow(label = "VA japonés", value = detail?.japaneseVa)
            DetailRow(label = "VA inglés", value = detail?.englishVa)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String?) {
    val visible = value?.isNotBlank() == true
    androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        if (visible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
                Text(text = value, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun FactsCard(
    left: List<Pair<String, String>>,
    right: List<Pair<String, String>>
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                left.forEach { (label, value) ->
                    KeyValue(label = label, value = value)
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                right.forEach { (label, value) ->
                    KeyValue(label = label, value = value)
                }
            }
        }
    }
}

@Composable
private fun KeyValue(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ChipsRow(values: List<String>) {
    if (values.isEmpty()) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        values.forEach { chipText ->
            AssistChip(
                onClick = { },
                label = { Text(chipText) },
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}


