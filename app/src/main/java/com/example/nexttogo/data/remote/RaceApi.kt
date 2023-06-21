package com.example.nexttogo.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.GET

interface RaceApi {
    @GET("racing/?method=nextraces&count=10")
    suspend fun allRaces(): AllRaceDetails
}

@JsonClass(generateAdapter = true)
data class AllRaceDetails(
    @Json(name = "data") val raceData: RaceData
) {
    @JsonClass(generateAdapter = true)
    data class RaceData(
        @Json(name = "race_summaries")
        val raceSummaries: Map<String, RaceSummary>
    )

    @JsonClass(generateAdapter = true)
    data class RaceSummary(
        @Json(name = "race_id") val raceId: String,
        @Json(name = "race_name") val raceName: String,
        @Json(name = "race_number") val raceNumber: Int,
        @Json(name = "meeting_id") val meetingId: String,
        @Json(name = "meeting_name") val meetingName: String,
        @Json(name = "category_id") val category: RaceCategory,
        @Json(name = "advertised_start") val advertisedStart: AdvertisedStart,
    ) {
        enum class RaceCategory{
            @Json(name = "9daef0d7-bf3c-4f50-921d-8e818c60fe61") GREYHOUND,
            @Json(name = "161d9be2-e909-4326-8c2c-35ed71fb460b") HARNESS,
            @Json(name = "4a2788f8-e825-4d36-9894-efd4baf1cfae") HORSE
        }

        @JsonClass(generateAdapter = true)
        data class AdvertisedStart(
            @Json(name = "seconds") val seconds: Long
        )
    }
}
