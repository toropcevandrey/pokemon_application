package com.example.poke_app.di.modules

import com.example.poke_app.data.PokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(intercepter)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHeroesApiService(retrofit: Retrofit): PokeApiService =
        retrofit.create(PokeApiService::class.java)
}

const val BASE_URL = "https://api.pokemontcg.io/v2/"