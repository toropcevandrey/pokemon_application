package com.example.dota_hero_matches_app.presentation.features.heroes


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota_hero_matches_app.data.repository.heroes.HeroesRepository
import com.example.dota_hero_matches_app.model.HeroesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(private val heroesRepository: HeroesRepository) :
    ViewModel() {

    private val _heroesLiveData: MutableLiveData<HeroesModel> =
        MutableLiveData()
    var heroesLiveData: LiveData<HeroesModel> = _heroesLiveData



    fun getHeroesFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _heroesLiveData.postValue(heroesRepository.getHeroesFromApi())


            } catch (e: Exception) {

            }

        }
    }

    fun sendText() = "test"
}