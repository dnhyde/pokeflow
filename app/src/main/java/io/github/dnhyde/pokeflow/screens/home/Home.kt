package io.github.dnhyde.pokeflow.screens.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.github.dnhyde.pokeflow.model.PokemonBasicInfo
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.launch

@Composable
fun Home(homeViewModel: HomeViewModel, navController: NavController) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Home")

        homeViewModel.states.observeAsState().value?.let { uiState ->
            when (uiState) {
                is UIState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )

                is HomeViewModel.HomeViewState.DataLoadedState -> {

                    // trigger pagination (home-made, should be done with
                    // https://developer.android.com/jetpack/compose/lists#large-datasets )
                    val listState = rememberLazyListState()
                    val lastVisibleItemIndex = (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
                    val currentIndex = remember { mutableStateOf(0) }

                    LaunchedEffect(currentIndex) {
                        launch { listState.scrollToItem(currentIndex.value) }
                    }

                    if (lastVisibleItemIndex == listState.layoutInfo.totalItemsCount) {
                        currentIndex.value = lastVisibleItemIndex - (listState.layoutInfo.visibleItemsInfo.size)
                        homeViewModel.getMorePokemons()
                    }

                    LazyColumn(state = listState) {
                        itemsIndexed(items = uiState.pokemons, key = { _, pokemon -> pokemon.name }) {
                            index, pokemon ->
                            PokemonItem(index = index, pokemon = pokemon, onPokemonSelect = {})
                        }
                    }
                }

                is HomeViewModel.HomeViewState.ErrorState -> Toast.makeText(
                    LocalContext.current, uiState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
internal fun PokemonItem(
    index: Int,
    pokemon: PokemonBasicInfo,
    onPokemonSelect: (name: String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onPokemonSelect(pokemon.name) }
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberImagePainter(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png"
            ),
            contentDescription = pokemon.name,
            modifier = Modifier.height(50.dp)
        )
        Text(
            text = pokemon.name,
//            style = MaterialTheme.typography.h4,
        )
        Text(
            text = "#$index",
//            style = MaterialTheme.typography.h6,
        )
    }
}
