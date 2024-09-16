package com.example.pokemon_application.presentation.features.pokemonDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.pokemon_application.databinding.FragmentPokemonDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val viewModel: PokemonDetailsViewModel by viewModels()
    private lateinit var binding: FragmentPokemonDetailsBinding
    private val pokemonId by lazy {
        arguments?.getString("POKEMON_ID")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonDetailsBinding.inflate(layoutInflater)
        setObserver()

        pokemonId?.let { viewModel.getImageByIdFromRepository(it.toString()) }

        return binding.root
    }

    private fun setObserver(){
        viewModel.pokemonsLiveData.observe(viewLifecycleOwner){ state ->
            Glide.with(requireContext())
                .load(state)
                .into(binding.ivPokemonDetails)
        }
    }
}