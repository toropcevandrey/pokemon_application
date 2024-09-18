package com.example.pokemon_application.data.repository.pokemonsFeed

import com.example.pokemon_application.data.PokemonsApiService
import com.example.pokemon_application.model.PokemonsResponse
import javax.inject.Inject

class PokemonsFeedRepository @Inject constructor(
    private val pokemonsApiService: PokemonsApiService
) {
    suspend fun getAllPokemonsFromApi(): PokemonsResponse = pokemonsApiService.getAllPokemons()

    suspend fun getPokemonsByName(name: String): PokemonsResponse =
        pokemonsApiService.getPokemonByName("name:$name")
}