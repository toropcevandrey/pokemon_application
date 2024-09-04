package com.example.dota_hero_matches_app.presentation.features.heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dota_hero_matches_app.R

open class HeroesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val tv1: TextView = itemView.findViewById(R.id.cv_tv1)

    fun bind(text:String?){
        tv1.text = text
    }

    companion object {
        fun create(parent: ViewGroup): HeroesViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_heroes, parent, false)
            return HeroesViewHolder(view)
        }
    }

}