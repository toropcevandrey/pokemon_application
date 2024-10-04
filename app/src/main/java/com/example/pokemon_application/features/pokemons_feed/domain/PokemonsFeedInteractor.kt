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
    suspend fun getPokemonsFeedFromAPI() = pokemonsFeedRepository.getAllPokemonsFromAPI()

    suspend fun getPokemonsFavoritesFromDB() =
        pokemonFavoriteRepository.getAllPokemonsFromFavoriteDB()

    suspend fun generateListForViewModel(
    ): List<PokemonsFeedViewData> {
        val pokemonData = pokemonFavoriteRepository.getAllPokemonsFromFavoriteDB()
        return pokemonsFeedRepository.getAllPokemonsFromAPI().pokemonApiModels.map { element ->
            val isFavorite = pokemonData.any { it.id == element.id }
            PokemonsFeedViewData(
                id = element.id,
                name = element.name,
                image = element.pokemonImages.small,
                isFavorite = isFavorite
            )
        }
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