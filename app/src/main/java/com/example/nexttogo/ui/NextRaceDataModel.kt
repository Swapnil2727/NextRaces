package com.example.nexttogo.ui

import androidx.compose.runtime.Immutable
import com.example.nexttogo.data.repo.RaceSummary
import kotlinx.collections.immutable.ImmutableList
import java.time.Duration
import java.time.Instant

data class NextToGoState(
    val data: Data? = null,
    val isLoading: Boolean = true,
    val error: Error? = null,
    val filteredByCategory: RaceCategory? = null,
) {

    data class Data(
        val raceSummaries: ImmutableList<RaceSummary>,
        val localRaceSummaries: ImmutableList<RaceSummary>,
    )

    data class RaceSummary(
        val raceId: String,
        val raceName: String,
        val raceNumber: Int,
        val meetingName: String,
        val category: RaceCategory,
        val startTime: Long,
    )

    enum class RaceCategory {
        GREYHOUND,
        HARNESS,
        HORSE,
    }

    @Immutable
    data class Error(val exception: Exception)
}

fun RaceSummary.toRaceSummary() = NextToGoState.RaceSummary(
    raceId = raceId,
    raceName = raceName,
    raceNumber = raceNumber,
    meetingName = meetingName,
    category = category.toRaceCategory(),
    startTime = advertisedStart.startTime,
)


fun RaceSummary.RaceCategory.toRaceCategory(): NextToGoState.RaceCategory {
    return when (this) {
        RaceSummary.RaceCategory.GREYHOUND -> NextToGoState.RaceCategory.GREYHOUND
        RaceSummary.RaceCategory.HARNESS -> NextToGoState.RaceCategory.HARNESS
        RaceSummary.RaceCategory.HORSE -> NextToGoState.RaceCategory.HORSE
    }
}

fun convertToRemainingTime(seconds: Long): String {
    val currentTime = Instant.now()
    val futureTime = Instant.ofEpochSecond(seconds)
    val remainingMinutes = Duration.between(currentTime, futureTime).toMinutes()
    val remainingHours = remainingMinutes / 60
    val remainingDays = remainingHours / 24
    return when {
        remainingDays > 0 -> "${remainingDays}d"
        remainingHours > 0 -> "${remainingHours}h"
        remainingMinutes > 1 -> "${remainingMinutes}m"
        else -> "${Duration.between(currentTime, futureTime).seconds}s"
    }
}
