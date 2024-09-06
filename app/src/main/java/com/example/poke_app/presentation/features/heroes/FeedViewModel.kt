package com.example.poke_app.presentation.features.heroes


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poke_app.data.repository.heroes.FeedRepository
import com.example.poke_app.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedRepository: FeedRepository) :
    ViewModel() {

    private val _feedLiveData: MutableLiveData<List<Data>> =
        MutableLiveData()
    var feedLiveData: LiveData<List<Data>> = _feedLiveData


    fun getAllPokeCardsFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _feedLiveData.postValue(feedRepository.getAllPokeCardsFromApi().data)


            } catch (e: Exception) {

            }

        }
    }

}