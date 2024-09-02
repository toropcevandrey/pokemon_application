package com.example.dota_hero_matches_app.presentation.features.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dota_hero_matches_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesFragment : Fragment() {

    private val viewModel: HeroesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_heroes, container, false)

        val tvH = view.findViewById<TextView>(R.id.tv_heroes)

        tvH.text = viewModel.sendText()

        return view
    }
}