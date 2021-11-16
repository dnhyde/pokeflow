package io.github.dnhyde.pokeflow.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

        val currentIndex = remember { mutableStateOf(0) }

        Text(text = "Home")

        homeViewModel.states.observeAsState().value?.let { uiState ->
            when (uiState) {
                is UIState.Loading -> Toast.makeText(LocalContext.current, "loading", Toast.LENGTH_SHORT).show()

                is HomeViewModel.HomeViewState.DataLoadedState -> {

                    // trigger pagination (home-made, should be done with
                    // https://developer.android.com/jetpack/compose/lists#large-datasets )
                    val listState = rememberLazyListState()
                    val lastVisibleItemIndex = (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                    LaunchedEffect(listState.layoutInfo.totalItemsCount) {
                        launch { listState.scrollToItem(currentIndex.value) }
                    }

                    if (lastVisibleItemIndex == listState.layoutInfo.totalItemsCount) {
                        currentIndex.value = lastVisibleItemIndex - (listState.layoutInfo.visibleItemsInfo.size - 1)
                        homeViewModel.getMorePokemons()
                    }

                    LazyColumn(state = listState) {
                        items(items = uiState.pokemons, key = { pokemon -> pokemon.name }) { pokemon ->
                            Text(modifier = Modifier.padding(25.dp).fillMaxWidth(), text = pokemon.name)
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
