package io.github.dnhyde.pokeflow.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.dnhyde.pokeflow.networking.PokeApiClient
import io.github.dnhyde.pokeflow.networking.PokeApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule() {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(baseUlr)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokeApiService(retrofit: Retrofit): PokeApiService {
        return retrofit.create(PokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokeApiClient(pokeApiService: PokeApiService): PokeApiClient {
        return PokeApiClient(pokeApiService)
    }

    companion object {
        const val baseUlr = "https://pokeapi.co/api/v2/"
    }
}
