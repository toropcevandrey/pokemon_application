package com.example.poke_app.presentation.features.pokemonsAllCardsPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.poke_app.R

open class PokemonsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val tvPokemonName: TextView = itemView.findViewById(R.id.tv_pokemon_name)
    private val ivPokemon: ImageView = itemView.findViewById(R.id.iv_pokemon_image)

    fun bind(text:String, image: String){
        tvPokemonName.text = text
        Glide.with(itemView)
            .load(image)
            .into(ivPokemon)
    }

    companion object {
        fun create(parent: ViewGroup): PokemonsViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_heroes, parent, false)
            return PokemonsViewHolder(view)
        }
    }

}