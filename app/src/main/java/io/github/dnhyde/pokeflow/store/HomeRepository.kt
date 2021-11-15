package io.github.dnhyde.pokeflow.store

import android.util.Log
import io.github.dnhyde.pokeflow.model.PokemonBasicInfo
import io.github.dnhyde.pokeflow.networking.PokeApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val pokeApiService: PokeApiService) {

    private var offset = 0

    suspend fun getPokemonPaged(): Result<List<PokemonBasicInfo>> {
        return kotlin.runCatching {
            val response = pokeApiService.getPokemonPaged(offset = offset)
            Log.d("HomeRepo", "response")
            Log.d("HomeRepo", response.toString())

            response.next?.let {
                offset = it.split("offset=").last().split("&").first().toInt()
            }

            response.results
        }
    }
}
