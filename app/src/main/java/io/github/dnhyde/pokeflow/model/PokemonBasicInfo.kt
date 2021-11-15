package io.github.dnhyde.pokeflow.model

data class PokemonPagedResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasicInfo>
)
data class PokemonBasicInfo(val name: String, val url: String)
