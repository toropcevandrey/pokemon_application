package com.example.pokemon_application.features.pokemon_details.data.model

import com.example.pokemon_application.features.pokemons_feed.data.model.PokemonApiModel
import com.google.gson.annotations.SerializedName

data class PokemonsDetailsResponse(
    @SerializedName("data")
    val pokemonApiModels: PokemonApiModel,
)
