package io.github.dnhyde.pokeflow.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import io.github.dnhyde.pokeflow.R
import io.github.dnhyde.pokeflow.model.PokemonBasicInfo
import io.uniflow.android.livedata.states
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun Home(homeViewModel: HomeViewModel, navController: NavController) {

    // Navigate when country selected
    LaunchedEffect(homeViewModel.navAction) {
        homeViewModel.navAction.collect {
            navController.navigate(it)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val currentIndex = remember { mutableStateOf(0) }

        Text(
            text = stringResource(id = R.string.title_home),
            style = MaterialTheme.typography.h4,
        )

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

                    LaunchedEffect(currentIndex.value) {
                        launch { listState.scrollToItem(currentIndex.value) }
                    }

                    if (lastVisibleItemIndex == listState.layoutInfo.totalItemsCount) {
                        currentIndex.value = lastVisibleItemIndex - (listState.layoutInfo.visibleItemsInfo.size)
                        homeViewModel.getMorePokemons()
                    }

                    LazyColumn(state = listState) {
                        itemsIndexed(items = uiState.pokemons, key = { _, pokemon -> pokemon.name }) {
                            index, pokemon ->
                            PokemonItem(index = index + 1, pokemon = pokemon, onPokemonSelect = {
                                homeViewModel
                                    .openPokemonDetail(pokemon.name)
                            })
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
            .padding(top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GlideImage(
            imageModel =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png",
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
            },
            contentDescription = pokemon.name,
            modifier = Modifier.size(60.dp)
                .clip(CircleShape)
                .background(Color.Black)
                .border(2.dp, Color.Black, CircleShape)
        )
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.body2,
        )
        Text(
            text = "#$index",
            style = MaterialTheme.typography.h5,
        )
    }
}
