package io.github.dnhyde.pokeflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import io.github.dnhyde.pokeflow.screens.detail.PokemonDetail
import io.github.dnhyde.pokeflow.screens.home.Home
import io.github.dnhyde.pokeflow.ui.theme.PokeflowTheme
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // image caching
        // https://github.com/coil-kt/coil/issues/409
        val imageLoader = ImageLoader.Builder(applicationContext).dispatcher(Dispatchers.Default)
            .build()
        Coil.setImageLoader(imageLoader)

        setContent {
            PokeflowTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()

                    NavHost(navController, startDestination = Routes.Home.path) {
                        composable(Routes.Home.path) {
                            Home(hiltViewModel(), navController)
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
        Home(hiltViewModel(), rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    PokeflowTheme {
        PokemonDetail()
    }
}
