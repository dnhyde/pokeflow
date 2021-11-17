package io.github.dnhyde.pokeflow.screens.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.dnhyde.pokeflow.Routes
import io.github.dnhyde.pokeflow.model.PokemonBasicInfo
import io.github.dnhyde.pokeflow.store.HomeRepository
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : AndroidDataFlow() {

    private val pokemons = mutableListOf<PokemonBasicInfo>()

    private val _navAction = MutableSharedFlow<String>(0)
    val navAction: SharedFlow<String> = _navAction

    init {
        getMorePokemons()
    }

    fun getMorePokemons() {
        viewModelScope.launch {
            setState(UIState.Loading)
            val pokemonList = homeRepository.getPokemonPaged()
            pokemonList.fold({
                pokemons.addAll(it)
                setState(HomeViewState.DataLoadedState(pokemons))
            }, {
                setState(HomeViewState.ErrorState("Could not get more pokemons now, try again in a while "))
            })
        }
    }

    fun openPokemonDetail(pokemonName: String) {
        viewModelScope.launch {
            _navAction.emit(Routes.PokemonDetail.path.replace("{name}", pokemonName))
        }
    }

    sealed class HomeViewState : UIState() {
        data class DataLoadedState(val pokemons: List<PokemonBasicInfo>) : HomeViewState()
        data class ErrorState(val message: String) : HomeViewState()
    }
}
