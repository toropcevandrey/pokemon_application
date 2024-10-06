package com.example.pokemon_application.features.pokemons_favorite.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pokemon_application.databinding.CardViewPokemonBinding
import com.example.pokemon_application.features.pokemons_feed.presentation.PokemonsScreenViewData

class PokemonsFavoriteListAdapter(
    private val onClickListener: OnPokemonClickListener
) :
    ListAdapter<PokemonsScreenViewData, PokemonsFavoriteViewHolder>(PokemonsFavoriteComparator()) {

    interface OnPokemonClickListener {
        fun onPokemonClick(id: String)
        fun onFavoriteClick(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsFavoriteViewHolder {
        val binding =
            CardViewPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonsFavoriteViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: PokemonsFavoriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id, current.name, current.image, current.isFavorite)
    }

    override fun onBindViewHolder(
        holder: PokemonsFavoriteViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val current = getItem(position)
        if (payloads.isEmpty()) {
            holder.bind(current.id, current.name, current.image, current.isFavorite)
        } else {
            if (payloads[0] == true) {
                holder.bind(current.id, current.name, current.image, current.isFavorite)
            }
        }
    }
}