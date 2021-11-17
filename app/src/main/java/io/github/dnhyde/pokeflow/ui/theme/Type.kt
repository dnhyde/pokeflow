package io.github.dnhyde.pokeflow.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.dnhyde.pokeflow.R

val fontPokemon = FontFamily(
    Font(R.font.pokemon_solid)
)

// Set of Material typography styles to start with
val Typography = Typography(

    h3 = TextStyle(
        fontFamily = fontPokemon,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.5.sp
    ),

    h4 = TextStyle(
        fontFamily = fontPokemon,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp
    ),

    h5 = TextStyle(
        fontFamily = fontPokemon,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),

    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
