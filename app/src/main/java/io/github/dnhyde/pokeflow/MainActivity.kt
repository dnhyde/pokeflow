package io.github.dnhyde.pokeflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.dnhyde.pokeflow.screens.detail.PokemonDetail
import io.github.dnhyde.pokeflow.screens.home.Home
import io.github.dnhyde.pokeflow.ui.theme.PokeflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeflowTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = rememberNavController(), startDestination = Routes.Home.path) {
                        composable(Routes.Home.path) {
                            Home()
                        }
                        composable(Routes.PokemonDetail.path) {
                            PokemonDetail()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PokeflowTheme {
        Home()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    PokeflowTheme {
        PokemonDetail()
    }
}
