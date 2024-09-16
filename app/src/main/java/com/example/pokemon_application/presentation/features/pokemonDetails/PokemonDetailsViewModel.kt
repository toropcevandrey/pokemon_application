package com.example.pokemon_application.presentation.features.pokemonDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_application.data.repository.pokemonDetails.PokemonDetailsRepository
import com.example.pokemon_application.model.PokemonApiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val pokemonDetailsRepository: PokemonDetailsRepository) :
    ViewModel() {
    private val _pokemonsLiveData: MutableLiveData<String> =
        MutableLiveData()

    var pokemonsLiveData: LiveData<String> = _pokemonsLiveData

    fun getImageByIdFromRepository(id: String) {

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