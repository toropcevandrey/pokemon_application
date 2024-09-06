package com.example.poke_app.presentation.features.heroes

import androidx.recyclerview.widget.DiffUtil
import com.example.poke_app.model.Data

class HeroesDataComparator : DiffUtil.ItemCallback<Data>(){
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}