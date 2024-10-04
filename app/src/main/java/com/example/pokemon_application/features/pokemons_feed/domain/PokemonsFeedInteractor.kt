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
        val pokemonsResponse = pokemonsFeedRepository.getAllPokemonsFromAPI().pokemonApiModels
        val pokemonData = pokemonFavoriteRepository.getAllPokemonsFromFavoriteDB()
        val list: MutableList<PokemonsFeedViewData> = mutableListOf()
        pokemonsResponse.forEach { e ->
            val isFavorite = pokemonData.any{it.id == e.id}
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