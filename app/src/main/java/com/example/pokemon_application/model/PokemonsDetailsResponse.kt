package com.example.pokemon_application.model

import com.google.gson.annotations.SerializedName

data class PokemonsDetailsResponse(
    @SerializedName("data")
    val pokemonApiModels: PokemonApiModel,
)
