package io.github.dnhyde.pokeflow.screens.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.dnhyde.pokeflow.R
import io.github.dnhyde.pokeflow.model.PokemonDetailInfo
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.UIState

@Composable
fun PokemonDetail(
    pokemonDetailViewModel: PokemonDetailViewModel,
    navController: NavController,
    pokemonName: String?,
) {

    // Fetch pokemon details from pokemon name received as argument
    LaunchedEffect(pokemonName) {
        pokemonName?.let { pokemonDetailViewModel.getPokemonDetail(pokemonName = it) }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(id = R.string.title_detail),
            style = MaterialTheme.typography.h4
        )
        pokemonName?.let { Text(text = it) }

        Spacer(modifier = Modifier.padding(top = 32.dp))

        pokemonDetailViewModel.states.observeAsState().value?.let { uiState ->
            when (uiState) {
                is UIState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )

                is PokemonDetailViewModel.PokemonDetailViewState.DataLoadedState -> {
                    PokemonInfoBox(pokemonInfo = uiState.pokemonInfo)
                }

                is PokemonDetailViewModel.PokemonDetailViewState.ErrorState -> Toast.makeText(
                    LocalContext.current, uiState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
internal fun PokemonInfoBox(pokemonInfo: PokemonDetailInfo) {
    Text(text = pokemonInfo.id.toString())
}
