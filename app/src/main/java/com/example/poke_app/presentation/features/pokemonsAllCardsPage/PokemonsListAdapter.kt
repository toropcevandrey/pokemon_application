package com.example.poke_app.presentation.features.pokemonsAllCardsPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.poke_app.databinding.CardViewHeroesBinding

class PokemonsListAdapter :
    ListAdapter<PokemonsViewData, PokemonsViewHolder>(PokemonsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonsViewHolder {
        val binding =
            CardViewHeroesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.image)
    }
}