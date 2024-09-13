package com.example.poke_app.presentation.features.pokemonsFeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke_app.data.repository.pokemons.PokemonsFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonsFeedViewModel @Inject constructor(private val pokemonsFeedRepository: PokemonsFeedRepository) :
    ViewModel() {

    private val _pokemonsLiveData: MutableLiveData<PokemonsFeedState> =
        MutableLiveData(PokemonsFeedState.Loading)

    var pokemonsLiveData: LiveData<PokemonsFeedState> = _pokemonsLiveData

    fun getAllPokeCardsFromRepository() {
        var pokemons: List<PokemonsFeedViewData>
        _pokemonsLiveData.value = PokemonsFeedState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                pokemons =
                    pokemonsFeedRepository.getAllPokemonsFromApi().pokemonApiModels.map { it ->
                        PokemonsFeedViewData(
                            id = it.id,
                            name = it.name,
                            image = it.pokemonImages.small
                        )
                    }
                withContext(Dispatchers.Main) {
                    _pokemonsLiveData.value = PokemonsFeedState.Success(pokemons)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsLiveData.value = PokemonsFeedState.Error
                }
            }
        }
    }
}