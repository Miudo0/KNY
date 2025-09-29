package com.empresa.kny

import android.os.Bundle
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.empresa.kny.ui.AllCharactersScreen
import com.empresa.kny.ui.CharacterDetailScreen

import com.empresa.kny.ui.theme.KNYTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KNYTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "list",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("list") {
                            AllCharactersScreen(
                                modifier = Modifier.fillMaxSize(),
                                onCharacterClick = { name, imageUrl ->
                                    if (name.isNotBlank()) {
                                        val encodedName = Uri.encode(name)
                                        val encodedImage = Uri.encode(imageUrl ?: "")
                                        navController.navigate("detail/${encodedName}?imageUrl=${encodedImage}")
                                    }
                                }
                            )
                        }
                        composable(
                            route = "detail/{name}?imageUrl={imageUrl}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType },
                                navArgument("imageUrl") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                }
                            )
                        ) { backStackEntry ->
                            val nameArg = Uri.decode(backStackEntry.arguments?.getString("name").orEmpty())
                            val imageArgRaw = backStackEntry.arguments?.getString("imageUrl")
                            val imageArg = imageArgRaw?.takeIf { it.isNotEmpty() }?.let { Uri.decode(it) }
                            CharacterDetailScreen(
                                name = nameArg,
                                initialImageUrl = imageArg,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}