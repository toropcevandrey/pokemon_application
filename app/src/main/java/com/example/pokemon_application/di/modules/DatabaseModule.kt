package com.example.pokemon_application.di.modules

import android.content.Context
import androidx.room.Room
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonDao
import com.example.pokemon_application.features.pokemons_favorite.data.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase): PokemonDao =
        pokemonDatabase.pokemonDao()

    @Provides
    @Singleton
    fun providePokemonsDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context = context,
            PokemonDatabase::class.java,
            "pokemons"
        ).fallbackToDestructiveMigration()
            .build()
    }
}