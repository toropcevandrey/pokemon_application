package com.example.poke_app.presentation.features.pokemonsAllCardsPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke_app.data.repository.pokemons.PokemonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(private val pokemonsRepository: PokemonsRepository) :
    ViewModel() {

    private val _pokemonsLiveData: MutableLiveData<PokemonsState> =
        MutableLiveData()   //TODO Добавить (PokemonsState.Loading)

    var pokemonsLiveData: LiveData<PokemonsState> = _pokemonsLiveData

    fun getAllPokeCardsFromRepository() {
        var pokemons: List<PokemonsViewData>
//        _pokemonsLiveData.value = PokemonsState.Loading //TODO включить после добавления разметки
        viewModelScope.launch(Dispatchers.IO) {
            try {
                pokemons = pokemonsRepository.getAllPokemonsFromApi().pokemonApiModels.map { it ->
                    PokemonsViewData(
                        id = it.id,
                        name = it.name,
                        image = it.pokemonImages.small
                    )
                }
                withContext(Dispatchers.Main) {
                    _pokemonsLiveData.value = PokemonsState.Success(pokemons)
                }
            } catch (e: Exception) {
                _pokemonsLiveData.value = PokemonsState.Error
            }
        }
    }
}