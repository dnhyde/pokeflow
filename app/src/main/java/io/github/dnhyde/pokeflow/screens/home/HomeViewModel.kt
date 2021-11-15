package io.github.dnhyde.pokeflow.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.dnhyde.pokeflow.store.HomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val pokemonList = homeRepository.getPokemonPaged()
            pokemonList.fold({
                Log.d("HomeVM", pokemonList.toString())
            }, {
            })
        }
    }
}
