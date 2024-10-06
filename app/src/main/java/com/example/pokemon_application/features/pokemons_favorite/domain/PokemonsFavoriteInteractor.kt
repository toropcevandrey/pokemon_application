package com.example.pokemon_application.features.pokemons_favorite.domain

import com.example.pokemon_application.features.pokemons_favorite.data.PokemonFavoriteRepository
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreenViewData
import javax.inject.Inject

class PokemonsFavoriteInteractor @Inject constructor(
    private val pokemonsFavoriteRepository: PokemonFavoriteRepository
) {
    suspend fun generateListForViewModel(): List<PokemonsScreenViewData> {
        return pokemonsFavoriteRepository.getAllPokemonsFromFavoriteDB().map {
            PokemonsScreenViewData(id = it.id, name = it.name, image = it.image, isFavorite = true)
        }
    }

    suspend fun deleteFavoritePokemonFromDB(id: String) {
        pokemonsFavoriteRepository.deletePokemonFromFavoriteDB(id)
    }
}