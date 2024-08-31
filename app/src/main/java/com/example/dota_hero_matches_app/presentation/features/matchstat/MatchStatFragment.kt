package com.example.dota_hero_matches_app.presentation.features.matchstat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dota_hero_matches_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchStatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_stat, container, false)
    }

}