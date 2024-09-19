package com.example.pokemon_application.features.pokemon_details.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_application.features.pokemon_details.data.PokemonDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonDetailsRepository: PokemonDetailsRepository
) : ViewModel() {

    private val navArgs = PokemonDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _pokemonsLiveData: MutableLiveData<String> =
        MutableLiveData()
    val pokemonsLiveData: LiveData<String> = _pokemonsLiveData

    init {
        getImageByIdFromRepository(navArgs.pokemonId)
    }

    private fun getImageByIdFromRepository(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val image =
                    pokemonDetailsRepository.getPokemonDetailsByIdFromApi(id).pokemonApiModels.pokemonImages.large
                withContext(Dispatchers.Main) {
                    _pokemonsLiveData.value = image
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("Error", "$e")
                }
            }
        }
    }
}