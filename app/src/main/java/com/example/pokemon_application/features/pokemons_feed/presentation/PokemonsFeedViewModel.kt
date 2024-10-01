package com.example.pokemon_application.features.pokemons_feed.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonData
import com.example.pokemon_application.features.pokemons_feed.data.model.PokemonApiModel
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

    val pokemonsFeedForView: MutableList<PokemonsFeedViewData> = mutableListOf()
    val pokemonsFavoriteInDB: MutableList<PokemonData> = mutableListOf()
    val pokemonsFeedFromAPI: MutableList<PokemonApiModel> = mutableListOf()

    init {
        loadPokemonsFeedFromAPI()
    }

    fun loadPokemonsFeedFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonsFeedLiveData.postValue(PokemonsFeedState.Loading)
            try {
                pokemonsFeedForView.clear()
                pokemonsFeedFromAPI.addAll(pokemonsFeedInteractor.getPokemonsFeedFromAPI().pokemonApiModels)
                pokemonsFavoriteInDB.addAll(pokemonsFeedInteractor.getPokemonsFavoritesFromDB())
                pokemonsFeedForView.addAll(
                    pokemonsFeedInteractor.generateListForViewModel(
                        pokemonsFeedFromAPI,
                        pokemonsFavoriteInDB
                    )
                )
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Success(pokemonsFeedForView)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Error
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
                    // Меняем статус избранного
                    val isFavoriteNow = !pokemon.isFavorite
                    pokemonsFeedInteractor.switchPokemonFavoriteInDB(
                        pokemon.id,
                        pokemon.name,
                        pokemon.image
                    )
                    // Обновляем локально данные во ViewModel
                    val updatedPokemon = pokemon.copy(isFavorite = isFavoriteNow)
                    val index = pokemonsFeedForView.indexOf(pokemon)
                    if (index != -1) {
                        pokemonsFeedForView[index] = updatedPokemon
                    }
                    withContext(Dispatchers.Main) {
                        _pokemonsFeedLiveData.value =
                            PokemonsFeedState.Success(pokemonsFeedForView.toList())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Error
                }
            }
        }
    }
}