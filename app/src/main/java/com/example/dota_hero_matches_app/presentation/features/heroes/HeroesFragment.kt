package com.example.dota_hero_matches_app.presentation.features.heroes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dota_hero_matches_app.R
import com.example.dota_hero_matches_app.databinding.FragmentHeroesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesFragment : Fragment() {

    private val viewModel: HeroesViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val adapter:HeroesListAdapter by lazy {HeroesListAdapter()}
    private lateinit var binding: FragmentHeroesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroesBinding.inflate(layoutInflater)
        initViews()
        setObservers()
        viewModel.getHeroesFromRepository()
        return binding.root
    }

    private fun initViews(){
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    private fun setObservers() {

        viewModel.heroesLiveData.observe(viewLifecycleOwner) { item ->
            adapter.submitList(item)
        }
    }
}