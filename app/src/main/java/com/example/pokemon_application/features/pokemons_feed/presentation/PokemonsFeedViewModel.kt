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

    private val _pokemonsFeedStateLiveData: MutableLiveData<PokemonsScreensViewState> =
        MutableLiveData(PokemonsScreensViewState.Loading)

    val pokemonsFeedStateLiveData: LiveData<PokemonsScreensViewState> = _pokemonsFeedStateLiveData

    private val openPokemonDetails = SingleLiveEvent<String>()

    val observeOpenPokemonDetails: LiveData<String> = openPokemonDetails

    private val pokemonsFeedForView: MutableList<PokemonRecyclerViewItem> =
        mutableListOf()

    private var page: Int = 1

    private var isLoaded: Boolean = false

    var currentScrollPosition: Int = 0

    init {
        if (pokemonsFeedForView.isEmpty()) {
            loadPokemonsFeedFromInteractor()
        }
    }

    fun loadPokemonsFeedFromInteractor() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _pokemonsFeedStateLiveData.value =
                    PokemonsScreensViewState.Loading
            }
            try {
                page = 1
                pokemonsFeedForView.clear()
                pokemonsFeedForView.addAll(pokemonsFeedInteractor.generateListForViewModel(page))

                withContext(Dispatchers.Main) {
                    _pokemonsFeedStateLiveData.value =
                        PokemonsScreensViewState.Success(pokemonsFeedForView)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedStateLiveData.value =
                        PokemonsScreensViewState.Error
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLoaded) {
            isLoaded = true
            page++
            viewModelScope.launch(Dispatchers.IO) {
                if (!pokemonsFeedForView.contains(LoadingItem(true))) {
                    pokemonsFeedForView.add(LoadingItem(true))
                    withContext(Dispatchers.Main) {
                        _pokemonsFeedStateLiveData.value = PokemonsScreensViewState.Success(
                            pokemonsFeedForView
                        )
                    }
                    try {
                        pokemonsFeedForView.addAll(
                            pokemonsFeedInteractor.generateListForViewModel(
                                page
                            )
                        )
                        pokemonsFeedForView.remove(LoadingItem(true))
                        withContext(Dispatchers.Main) {
                            _pokemonsFeedStateLiveData.value =
                                PokemonsScreensViewState.Success(
                                    pokemonsFeedForView
                                )
                        }
                        isLoaded = false
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            _pokemonsFeedStateLiveData.value =
                                PokemonsScreensViewState.Error
                        }
                        isLoaded = false
                    }
                } else {
                    return@launch
                }
            }
        } else {
            return
        }
    }

    fun onPokemonClicked(id: String) {
        openPokemonDetails.value = id
    }

    fun onFavoriteClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemon =
                    pokemonsFeedForView.find { it is PokemonsScreenViewData && it.id == id } as? PokemonsScreenViewData
                if (pokemon != null) {
                    pokemonsFeedInteractor.switchPokemonFavoriteInDB(
                        pokemon.id, pokemon.name, pokemon.image
                    )
                    val isFavorite = pokemonsFeedInteractor.isPokemonInFavoriteDB(id)

                    val updatedPokemon = pokemon.copy(isFavorite = isFavorite)

                    pokemonsFeedForView.replaceAll {
                        if (it is PokemonsScreenViewData && it.id == updatedPokemon.id) {
                            updatedPokemon
                        } else {
                            it
                        }
                    }

                    withContext(Dispatchers.Main) {
                        _pokemonsFeedStateLiveData.value =
                            PokemonsScreensViewState.Success(
                                pokemonsFeedForView.toList()
                            )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedStateLiveData.value =
                        PokemonsScreensViewState.Error
                }
            }
        }
    }

    fun onScrollPositionChanged(position: Int) {
        currentScrollPosition = position
    }
}