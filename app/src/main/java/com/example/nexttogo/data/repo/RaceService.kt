package com.example.nexttogo.data.repo

import com.example.nexttogo.data.remote.AllRaceDetails
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDateTime

interface RaceService {
    suspend fun getAllRaces(): Flow<com.example.nexttogo.data.repo.AllRaceDetails>
}

data class AllRaceDetails(
    val data: RaceData
)

data class RaceData(
    val raceSummaries: Map<String, RaceSummary>
)

data class RaceSummary(
    val raceId: String,
    val raceName: String,
    val raceNumber: Int,
    val meetingId: String,
    val meetingName: String,
    val category: RaceCategory,
    val advertisedStart: AdvertisedStart,
) {
    enum class RaceCategory {
        GREYHOUND,
        HARNESS,
        HORSE
    }

    data class AdvertisedStart(
        val startTime: Long
    )
}

fun AllRaceDetails.toAllRaceDetails() = com.example.nexttogo.data.repo.AllRaceDetails(
    data = raceData.toRaceData()
)

fun AllRaceDetails.RaceData.toRaceData() = RaceData(
    raceSummaries = raceSummaries.mapValues { it.value.toRaceSummary() }
)

fun AllRaceDetails.RaceSummary.toRaceSummary() = RaceSummary(
    raceId = raceId,
    raceName = raceName,
    raceNumber = raceNumber,
    meetingId = meetingId,
    meetingName = meetingName,
    category = toRaceCategory(),
    advertisedStart = advertisedStart.toAdvertisedStart(),
)

fun AllRaceDetails.RaceSummary.toRaceCategory(): RaceSummary.RaceCategory {
    return when (category) {
        AllRaceDetails.RaceSummary.RaceCategory.GREYHOUND -> RaceSummary.RaceCategory.GREYHOUND
        AllRaceDetails.RaceSummary.RaceCategory.HARNESS -> RaceSummary.RaceCategory.HARNESS
        AllRaceDetails.RaceSummary.RaceCategory.HORSE -> RaceSummary.RaceCategory.HORSE
    }
}

fun AllRaceDetails.RaceSummary.AdvertisedStart.toAdvertisedStart() = RaceSummary.AdvertisedStart(
    startTime = seconds
)