package com.example.pokemon_application.features.pokemons_feed.domain

import com.example.pokemon_application.features.pokemons_favorite.data.PokemonData
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonFavoriteRepository
import com.example.pokemon_application.features.pokemons_feed.data.PokemonsFeedRepository
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsFeedViewData
import javax.inject.Inject

class PokemonsFeedInteractor @Inject constructor(
    private val pokemonsFeedRepository: PokemonsFeedRepository,
    private val pokemonFavoriteRepository: PokemonFavoriteRepository
) {
    suspend fun toggleFavorite(id: String, name: String, image: String) {
        val exist: Boolean = pokemonFavoriteRepository.ifPokemonFavorite(id)
        if (exist) {
            pokemonFavoriteRepository.deletePokemonFromFavorite(id)
        } else {
            pokemonFavoriteRepository.addPokemonToFavorite(
                PokemonData(id = id, name = name, image = image)
            )
        }
    }

    suspend fun getPokemons() = pokemonsFeedRepository.getAllPokemonsFromApi()

    suspend fun getFavorites() = pokemonFavoriteRepository.getAllPokemons()
}