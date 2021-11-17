package io.github.dnhyde.pokeflow.model

import com.squareup.moshi.Json

data class PokemonDetailInfo(
    val id: Int,
    val name: String,
    @Json(name = "base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,

    val sprites: Sprites,

    val stats: List<Stat>,
    val types: List<Type>
)

data class Sprites(val other: OtherSprites)
data class OtherSprites(val home: HomeSprites)
data class HomeSprites(
    @Json(name = "front_default")
    val spriteUrl: String,
)

data class Stat(
    @Json(name = "base_stat")
    val base: Int,
    @Json(name = "stat")
    val statInfo: StatInfo,
)
data class StatInfo(val name: String)

data class Type(val type: TypeInfo)
data class TypeInfo(val name: String)
