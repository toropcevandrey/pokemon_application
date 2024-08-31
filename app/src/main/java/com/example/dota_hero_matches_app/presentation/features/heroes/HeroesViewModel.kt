package com.example.dota_hero_matches_app.presentation.features.heroes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota_hero_matches_app.data.HeroesApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(private val heroesApiService: HeroesApiService) :
    ViewModel() {



    init {
        viewModelScope.launch {
            val a: String = heroesApiService.getHeroes().get(1).name
            Log.d("ZAPROS", a)
        }

    }

    fun sendText() = "xyi"

}