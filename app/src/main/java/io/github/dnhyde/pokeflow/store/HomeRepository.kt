package io.github.dnhyde.pokeflow.store

import io.github.dnhyde.pokeflow.networking.PokeApiClient
import javax.inject.Inject

class HomeRepository @Inject constructor(private val pokeApiClient: PokeApiClient)
