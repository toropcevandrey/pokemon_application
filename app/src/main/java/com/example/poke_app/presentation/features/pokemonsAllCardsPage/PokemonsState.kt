package com.example.poke_app.presentation.features.pokemonsAllCardsPage

sealed class PokemonsState {
    class Success(val pokemons: List<PokemonsViewData>) : PokemonsState()
    object Error : PokemonsState()
    object Loading : PokemonsState()
}