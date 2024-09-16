package com.example.pokemon_application.presentation.features.pokemonsFeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_application.data.repository.pokemonsFeed.PokemonsFeedRepository
import com.example.pokemon_application.presentation.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonsFeedViewModel @Inject constructor(private val pokemonsFeedRepository: PokemonsFeedRepository) :
    ViewModel() {

    private val _pokemonsFeedLiveData: MutableLiveData<PokemonsFeedState> =
        MutableLiveData(PokemonsFeedState.Loading)

    var pokemonsFeedLiveData: LiveData<PokemonsFeedState> = _pokemonsFeedLiveData

    private val openPokemonDetails = SingleLiveEvent<String>()

    fun observeOpenPokemonDetails(): SingleLiveEvent<String> = openPokemonDetails

    fun sendPokemonId(id: String){
        openPokemonDetails.value = id
    }

    fun getAllPokeCardsFromRepository() {
        var pokemons: List<PokemonsFeedViewData>
        _pokemonsFeedLiveData.value = PokemonsFeedState.Loading
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
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Success(pokemons)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _pokemonsFeedLiveData.value = PokemonsFeedState.Error
                }
            }
        }
    }
}