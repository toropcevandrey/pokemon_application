package com.example.pokemon_application.features.pokemons_feed.data

import com.example.pokemon_application.features.pokemons_feed.data.model.PokemonsResponse
import javax.inject.Inject

class PokemonsFeedRepository @Inject constructor(
    private val pokemonsApiService: PokemonsApiService
) {
    suspend fun getAllPokemonsFromApi(): PokemonsResponse = pokemonsApiService.getAllPokemons()

    suspend fun getPokemonsByName(name: String): PokemonsResponse =
        pokemonsApiService.getPokemonByName("name:$name")
}