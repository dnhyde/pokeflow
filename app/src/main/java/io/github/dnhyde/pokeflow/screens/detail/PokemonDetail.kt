package io.github.dnhyde.pokeflow.screens.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PokemonDetail(
    pokemonDetailViewModel: PokemonDetailViewModel,
    navController: NavController,
    pokemonName: String?,
) {
    pokemonName?.let { Text(text = it) }
}
