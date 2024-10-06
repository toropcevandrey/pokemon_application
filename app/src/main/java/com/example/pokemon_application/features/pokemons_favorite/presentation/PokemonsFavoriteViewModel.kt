package com.example.pokemon_application.features.pokemons_favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_application.features.pokemons_favorite.domain.PokemonsFavoriteInteractor
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreensViewState
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreenViewData
import com.example.pokemon_application.utils.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonsFavoriteViewModel @Inject constructor(private val pokemonsFavoriteInteractor: PokemonsFavoriteInteractor) :
    ViewModel() {
    private val _pokemonsFavoriteLiveData: MutableLiveData<PokemonsScreensViewState> =
        MutableLiveData(PokemonsScreensViewState.Loading)

    val pokemonsFavoriteLiveData: LiveData<PokemonsScreensViewState> = _pokemonsFavoriteLiveData

    private val openPokemonDetails = SingleLiveEvent<String>()

    val observeOpenPokemonDetails: LiveData<String> = openPokemonDetails

    private val pokemonsFavoriteForView: MutableList<PokemonsScreenViewData> = mutableListOf()

    init {
        loadPokemonsFavoriteFromInteractor()
    }

    fun loadPokemonsFavoriteFromInteractor() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _pokemonsFavoriteLiveData.value = PokemonsScreensViewState.Loading
            }
            try {
                pokemonsFavoriteForView.clear()
                pokemonsFavoriteForView.addAll(pokemonsFavoriteInteractor.generateListForViewModel())
                withContext(Dispatchers.Main) {
                    _pokemonsFavoriteLiveData.value =
                        PokemonsScreensViewState.Success(pokemonsFavoriteForView)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFavoriteLiveData.value = PokemonsScreensViewState.Error
                }
            }
        }
    }

    fun onPokemonClicked(id: String) {
        openPokemonDetails.value = id
    }

    fun deletePokemonFromFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                pokemonsFavoriteInteractor.deleteFavoritePokemonFromDB(id)
                pokemonsFavoriteForView.clear()
                pokemonsFavoriteForView.addAll(pokemonsFavoriteInteractor.generateListForViewModel())
                withContext(Dispatchers.Main) {
                    _pokemonsFavoriteLiveData.value =
                        PokemonsScreensViewState.Success(pokemonsFavoriteForView.toList())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFavoriteLiveData.value = PokemonsScreensViewState.Error
                }
            }
        }
    }
}