package io.github.dnhyde.pokeflow.screens.home

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.UIState

@Composable
fun Home(homeViewModel: HomeViewModel, navController: NavController) {

    Text(text = "Home")

    homeViewModel.states.observeAsState().value?.let { uiState ->
        when (uiState) {
            is UIState.Loading -> Toast.makeText(LocalContext.current, "loading", Toast.LENGTH_SHORT).show()
            is HomeViewModel.HomeViewState.DataLoadedState -> {
                LazyColumn {
                    items(items = uiState.pokemons, key = { pokemon -> pokemon.name }) { pokemon ->
                        Text(text = pokemon.name)
                    }
                }
            }
            is HomeViewModel.HomeViewState.ErrorState -> Toast.makeText(
                LocalContext.current, uiState.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Button(onClick = { homeViewModel.getMorePokemons() }) {
        Text(text = "load")
    }
}
