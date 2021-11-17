package io.github.dnhyde.pokeflow.store

import io.github.dnhyde.pokeflow.model.PokemonDetailInfo
import io.github.dnhyde.pokeflow.networking.PokeApiService
import javax.inject.Inject

class PokemonDetailRepository @Inject constructor(private val pokeApiService: PokeApiService) {

    suspend fun getPokemonDetail(name: String): Result<PokemonDetailInfo> {
        return kotlin.runCatching {
            val response = pokeApiService.getPokemonDetail(name)
            response
        }
    }
}
