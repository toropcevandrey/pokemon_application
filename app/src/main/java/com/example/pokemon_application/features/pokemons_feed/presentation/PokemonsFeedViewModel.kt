package com.example.pokemon_application.features.pokemons_feed.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_application.features.pokemons_feed.domain.PokemonsFeedInteractor
import com.example.pokemon_application.utils.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonsFeedViewModel @Inject constructor(private val pokemonsFeedInteractor: PokemonsFeedInteractor) :
    ViewModel() {

    private val _pokemonsFeedLiveData: MutableLiveData<PokemonsFeedState> =
        MutableLiveData(PokemonsFeedState.Loading)

    val pokemonsFeedLiveData: LiveData<PokemonsFeedState> = _pokemonsFeedLiveData

    private val openPokemonDetails = SingleLiveEvent<String>()

    val observeOpenPokemonDetails: LiveData<String> = openPokemonDetails

    private val pokemonsFeedList: MutableList<PokemonsFeedViewData> = mutableListOf()

    fun onPokemonClicked(id: String) {
        openPokemonDetails.value = id
    }

    fun getAllPokeCardsFromRepository() {
        _pokemonsFeedLiveData.value = PokemonsFeedState.Loading
        updatePokemonsList()
    }

    private fun updatePokemonsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemonDataForView: MutableList<PokemonsFeedViewData> = mutableListOf()
                val pokemonsFeedFromApi = pokemonsFeedInteractor.getPokemons()
                val pokemonsFavorite = pokemonsFeedInteractor.getFavorites()
                pokemonsFeedFromApi.pokemonApiModels.forEach { feedItem ->

                    val found = pokemonsFavorite.any { it.id == feedItem.id }

                    pokemonDataForView.add(
                        PokemonsFeedViewData(
                            id = feedItem.id,
                            name = feedItem.name,
                            image = feedItem.pokemonImages.small,
                            isFavorite = found
                        )
                    )
                }
                pokemonsFeedList.clear()
                pokemonsFeedList.addAll(pokemonDataForView)
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Success(pokemonDataForView)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Error
                }
            }
        }
    }

    fun onFavoriteClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newList = pokemonsFeedList.map {
                if (it.id == id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    it
                }
            }

            pokemonsFeedList.clear()
            pokemonsFeedList.addAll(newList)

            val pokemon = pokemonsFeedList.find { it.id == id }
            if (pokemon != null) {
                pokemonsFeedInteractor.switchPokemonFavoriteInDB(pokemon.id, pokemon.name, pokemon.image)

                pokemon?.let {
                    pokemon.isFavorite = !pokemon.isFavorite
                    pokemonsFeedInteractor.switchPokemonFavoriteInDB(
                        pokemon.id,
                        pokemon.name,
                        pokemon.image
                    )
                }
                val pokemonDataForView: MutableList<PokemonsFeedViewData> = mutableListOf()

                val pokemonsFavorite = pokemonsFeedInteractor.getFavorites()

                pokemonsFeedList.forEach { feedItem ->

                    val found = pokemonsFavorite.any { it.id == feedItem.id }

                    pokemonDataForView.add(
                        PokemonsFeedViewData(
                            id = feedItem.id,
                            name = feedItem.name,
                            image = feedItem.image,
                            isFavorite = found
                        )
                    )
                }
                pokemonsFeedList.clear()
                pokemonsFeedList.addAll(pokemonDataForView)
            withContext(Dispatchers.Main) {
                _pokemonsFeedLiveData.value = PokemonsFeedState.Success(pokemonsFeedList)
            }
            }
        }
    }
}