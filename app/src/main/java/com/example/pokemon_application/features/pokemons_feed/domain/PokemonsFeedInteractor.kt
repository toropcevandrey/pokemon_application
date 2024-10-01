package com.example.pokemon_application.features.pokemons_feed.domain

import com.example.pokemon_application.features.pokemons_favorite.data.PokemonData
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonFavoriteRepository
import com.example.pokemon_application.features.pokemons_feed.data.PokemonsFeedRepository
import javax.inject.Inject

class PokemonsFeedInteractor @Inject constructor(
    private val pokemonsFeedRepository: PokemonsFeedRepository,
    private val pokemonFavoriteRepository: PokemonFavoriteRepository
) {
    suspend fun switchPokemonFavoriteInDB(id: String, name: String, image: String) {
        val exist: Boolean = pokemonFavoriteRepository.isPokemonInFavoriteDB(id)
        if (exist) {
            pokemonFavoriteRepository.deletePokemonFromFavoriteDB(id)
        } else {
            pokemonFavoriteRepository.addPokemonToFavoriteDB(
                PokemonData(id = id, name = name, image = image)
            )
        }
    }

    suspend fun getPokemons() = pokemonsFeedRepository.getAllPokemonsFromApi()

    suspend fun getFavorites() = pokemonFavoriteRepository.getAllPokemonsFromFavoriteDB()
}