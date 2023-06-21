package com.example.nexttogo.utils

import android.content.res.Resources
import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import com.example.nexttogo.data.remote.AllRaceDetails
import com.example.nexttogo.data.repo.RaceSummary
import io.github.serpro69.kfaker.faker
import java.time.Instant
import java.util.Random

val random = Random(0)

val faker = faker { }


@Suppress("NOTHING_TO_INLINE")
inline fun runAndroidComposeUiTest(noinline block: AndroidComposeUiTest<ComponentActivity>.() -> Unit) =
    androidx.compose.ui.test.runAndroidComposeUiTest { block() }

val <A : ComponentActivity> AndroidComposeUiTest<A>.resources: Resources get() = activity!!.resources


fun isList(): SemanticsMatcher = SemanticsMatcher.keyIsDefined(SemanticsProperties.CollectionInfo)

fun ComposeUiTest.assertSnackbarDisplayed(text: String) {
    onNode(isSnackbar()).assert(hasAnyDescendant(hasText(text)))
}

fun isSnackbar() =
    SemanticsMatcher.keyIsDefined(SemanticsProperties.LiveRegion) and
            SemanticsMatcher.keyIsDefined(SemanticsActions.Dismiss)

fun Random.nextPhotoUrl() = "https://example.com/photo/${nextInt()}.jpg"

fun Random.nextRaceSummary() = AllRaceDetails.RaceSummary(
    raceId = faker.idNumber.toString(),
    raceName = faker.name.toString(),
    raceNumber = nextInt(),
    meetingId = faker.idNumber.toString(),
    meetingName = faker.company.name(),
    category = AllRaceDetails.RaceSummary.RaceCategory.values().random(),
    advertisedStart = AllRaceDetails.RaceSummary.AdvertisedStart(Instant.now().epochSecond.plus(nextLong()))
)