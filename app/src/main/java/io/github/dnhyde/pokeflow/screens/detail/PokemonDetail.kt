package io.github.dnhyde.pokeflow.screens.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette
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
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Composable
internal fun PokemonInfoBox(pokemonInfo: PokemonDetailInfo) {

    var palette by remember { mutableStateOf<Palette?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(CircleShape)
                    .background(color = Color(palette?.mutedSwatch?.rgb ?: 0))
            ) {
                GlideImage(
                    imageModel = pokemonInfo.sprites.other.home.spriteUrl,
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter()
                    },
                    contentDescription = pokemonInfo.name,
                    bitmapPalette = BitmapPalette {
                        palette = it
                    }
                )
            }
        }
        item {
            Text(
                text = "#${pokemonInfo.id}",
                style = MaterialTheme.typography.h5,
            )
            Spacer(modifier = Modifier.padding(top = 32.dp))
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicInfoCell(
                    label = stringResource(id = R.string.label_experience),
                    value = pokemonInfo.baseExperience
                )
                BasicInfoCell(
                    label = stringResource(id = R.string.label_weight),
                    value = pokemonInfo.weight
                )
                BasicInfoCell(
                    label = stringResource(id = R.string.label_height),
                    value = pokemonInfo.height
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text(
                text = stringResource(id = R.string.label_stats),
                style = MaterialTheme.typography.h5,
            )
        }
        pokemonInfo.stats.forEach {
            item {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = "${it.statInfo.name}: ")
                    Text(text = it.base.toString())
                }
            }
        }

        item {
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text(
                text = stringResource(id = R.string.label_types),
                style = MaterialTheme.typography.h5,
            )
        }

        pokemonInfo.types.forEach {
            item {
                Text(text = it.type.name)
            }
        }
    }
}

@Composable
fun BasicInfoCell(label: String, value: Int) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.h5,
        )
        Text(text = value.toString())
    }
}
