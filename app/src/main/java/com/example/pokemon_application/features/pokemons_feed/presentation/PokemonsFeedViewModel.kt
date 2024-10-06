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

    private val _pokemonsFeedLiveData: MutableLiveData<PokemonsScreensViewState> =
        MutableLiveData(PokemonsScreensViewState.Loading)

    val pokemonsFeedLiveData: LiveData<PokemonsScreensViewState> = _pokemonsFeedLiveData

    private val openPokemonDetails = SingleLiveEvent<String>()

    val observeOpenPokemonDetails: LiveData<String> = openPokemonDetails

    private val pokemonsFeedForView: MutableList<PokemonsScreenViewData> = mutableListOf()

    init {
        loadPokemonsFeedFromAPI()
    }

    fun loadPokemonsFeedFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _pokemonsFeedLiveData.value = PokemonsScreensViewState.Loading
            }
            try {
                pokemonsFeedForView.clear()
                pokemonsFeedForView.addAll(pokemonsFeedInteractor.generateListForViewModel())
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value =
                        PokemonsScreensViewState.Success(pokemonsFeedForView)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsScreensViewState.Error
                }
            }
        }
    }

    fun onPokemonClicked(id: String) {
        openPokemonDetails.value = id
    }

    fun onFavoriteClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemon = pokemonsFeedForView.find { it.id == id }
                if (pokemon != null) {
                    pokemonsFeedInteractor.switchPokemonFavoriteInDB(
                        pokemon.id, pokemon.name, pokemon.image
                    )
                    val isFavorite = pokemonsFeedInteractor.isPokemonInFavoriteDB(id)

                    val updatedPokemon = pokemon.copy(isFavorite = isFavorite)

                    pokemonsFeedForView.replaceAll {
                        if (it.id == updatedPokemon.id) {
                            updatedPokemon
                        } else {
                            it
                        }
                    }

                    withContext(Dispatchers.Main) {
                        _pokemonsFeedLiveData.value =
                            PokemonsScreensViewState.Success(pokemonsFeedForView.toList())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsScreensViewState.Error
                }
            }
        }
    }
}