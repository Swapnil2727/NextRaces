package com.example.nexttogo.data.repo

import com.example.nexttogo.data.remote.RaceApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealRaceService @Inject constructor(
    private val raceApi: RaceApi,
) : RaceService {
    override suspend fun getAllRaces() = flow {
        emit(raceApi.allRaces().toAllRaceDetails())
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface RaceServiceModule {
    @Binds
    fun service(service: RealRaceService): RaceService
}