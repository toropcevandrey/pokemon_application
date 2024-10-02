package com.example.pokemon_application.features.pokemons_feed.domain

import com.example.pokemon_application.features.pokemons_favorite.data.PokemonData
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonFavoriteRepository
import com.example.pokemon_application.features.pokemons_feed.data.PokemonsFeedRepository
import com.example.pokemon_application.features.pokemons_feed.data.model.PokemonApiModel
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
        val pokemonsResponse = getPokemonsFeedFromAPI().pokemonApiModels
        val pokemonData = getPokemonsFavoritesFromDB()
        val list: MutableList<PokemonsFeedViewData> = mutableListOf()
        val favoriteList = pokemonData.map { it.id }
        pokemonsResponse.forEach { e ->
            val isFavorite = favoriteList.contains(e.id)
            list.add(
                PokemonsFeedViewData(
                    id = e.id,
                    name = e.name,
                    image = e.pokemonImages.small,
                    isFavorite = isFavorite
                )
            )
        }
        return list
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