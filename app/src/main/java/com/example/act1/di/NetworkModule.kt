package com.example.act1.di

import com.example.act1.data.remote.FootballApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideFootballApi(moshi: Moshi): FootballApi {
        return Retrofit.Builder()
            // He quitado el prefijo /api/ por si tu backend no lo usa. 
            // Si tu backend SI requiere /api/, por favor restauralo.
            .baseUrl("https://trabajomascotas-1-c.onrender.com/") 
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FootballApi::class.java)
    }
}
