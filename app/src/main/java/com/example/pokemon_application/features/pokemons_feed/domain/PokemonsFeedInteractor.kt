package com.example.pokemon_application.features.pokemons_feed.domain

import com.example.pokemon_application.features.pokemons_favorite.data.PokemonData
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonFavoriteRepository
import com.example.pokemon_application.features.pokemons_feed.data.PokemonsFeedRepository
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreenViewData
import javax.inject.Inject

class PokemonsFeedInteractor @Inject constructor(
    private val pokemonsFeedRepository: PokemonsFeedRepository,
    private val pokemonFavoriteRepository: PokemonFavoriteRepository
) {
    suspend fun generateListForViewModel(
        page: Int
    ): List<PokemonsScreenViewData> {
        val pokemonData = pokemonFavoriteRepository.getAllPokemonsFromFavoriteDB()
        return pokemonsFeedRepository.getAllPokemonsFromAPI(page).pokemonApiModels.map { element ->
            val isFavorite = pokemonData.any { it.id == element.id }
            PokemonsScreenViewData(
                id = element.id,
                name = element.name,
                image = element.pokemonImages.small,
                isFavorite = isFavorite
            )
        }
    }

    suspend fun isPokemonInFavoriteDB(id: String): Boolean {
        return pokemonFavoriteRepository.isPokemonInFavoriteDB(id)
    }

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
}