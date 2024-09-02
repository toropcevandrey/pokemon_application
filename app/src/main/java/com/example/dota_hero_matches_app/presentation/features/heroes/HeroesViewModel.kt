package com.example.dota_hero_matches_app.presentation.features.heroes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dota_hero_matches_app.data.HeroesApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(private val heroesApiService: HeroesApiService) :
    ViewModel() {

    init {
        val job = viewModelScope.launch(Dispatchers.Main) {
            repeat(10) { i ->
                Log.d("CORUTINA", "START")
                delay(1000)
                Log.d("CORUTINA", "DELAY END")
                val a: String = heroesApiService.getHeroes().get(i).name
                Log.d("ZAPROS", a)
            }
        }
        Log.d("CROUTINA", "CANCELED")
    }

    fun sendText() = "test"
}