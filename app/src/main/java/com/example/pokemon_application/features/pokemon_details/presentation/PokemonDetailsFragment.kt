package com.example.pokemon_application.features.pokemon_details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pokemon_application.databinding.FragmentPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(layoutInflater)
        setObserver()
        return binding.root
    }

    private fun setObserver() {
        viewModel.pokemonsLiveData.observe(viewLifecycleOwner, ObserverD())
    }

    inner class ObserverD : Observer<String> {
        override fun onChanged(value: String) {
            Glide.with(requireContext())
                .load(value)
                .into(binding.ivPokemonDetails)
        }
    }
}