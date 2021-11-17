package io.github.dnhyde.pokeflow.screens.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.dnhyde.pokeflow.model.PokemonDetailInfo
import io.github.dnhyde.pokeflow.store.PokemonDetailRepository
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonDetailRepository: PokemonDetailRepository) :
    AndroidDataFlow() {

    fun getPokemonDetail(pokemonName: String) {
        viewModelScope.launch {
            setState(UIState.Loading)
            val pokemonInfo = pokemonDetailRepository.getPokemonDetail(pokemonName)
            pokemonInfo.fold({
                setState(PokemonDetailViewState.DataLoadedState(it))
            }, {
                setState(
                    PokemonDetailViewState.ErrorState(
                        "Could not get pokemon info now!\nYou may be disconnected from Pallet Town..."
                    )
                )
            })
        }
    }

    sealed class PokemonDetailViewState : UIState() {
        data class DataLoadedState(val pokemonInfo: PokemonDetailInfo) : PokemonDetailViewState()
        data class ErrorState(val message: String) : PokemonDetailViewState()
    }
}
