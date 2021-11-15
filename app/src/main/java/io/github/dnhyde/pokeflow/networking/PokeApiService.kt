package io.github.dnhyde.pokeflow.networking

import io.github.dnhyde.pokeflow.model.PokemonDetailInfo
import io.github.dnhyde.pokeflow.model.PokemonPagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon/")
    suspend fun getPokemonPaged(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonPagedResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailInfo
}
