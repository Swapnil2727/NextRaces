package com.example.nexttogo.di

import com.example.nexttogo.data.remote.RaceApi
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
object AppModule {

    @Provides
    @Singleton
    fun provideRaceApi(): RaceApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RaceApi::class.java)
    }
}

val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

const val BASE_URL = "https://api.neds.com.au/rest/v1/"