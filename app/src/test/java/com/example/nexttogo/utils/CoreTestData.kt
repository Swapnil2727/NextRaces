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
import com.example.nexttogo.data.repo.RaceSummary
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.ui.toRaceSummary
import io.github.serpro69.kfaker.faker
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
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

fun Random.nextInstant(): Instant = "2000-01-01T00:00:00.00Z"
    .toOffsetDateTime()
    .plus(nextInt(1000).toLong(), ChronoUnit.YEARS)
    .plus(nextInt(12).toLong(), ChronoUnit.MONTHS)
    .plus(nextInt(365).toLong(), ChronoUnit.DAYS)
    .toInstant()


fun String.toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(this)

fun Random.nextRaceSummary(
    category: RaceSummary.RaceCategory = RaceSummary.RaceCategory.values()
        .random()
) = RaceSummary(
    raceId = random.nextInt().toString(),
    raceName = faker.name.toString(),
    raceNumber = nextInt(),
    meetingId = faker.idNumber.toString(),
    meetingName = faker.company.name(),
    category = category,
    advertisedStart = RaceSummary.AdvertisedStart(nextInstant().epochSecond)
)

fun Random.nextData(
    raceSummaries: ImmutableList<NextToGoState.RaceSummary> = List(10) { nextRaceSummary().toRaceSummary() }.toImmutableList(),
    localRaceSummaries: ImmutableList<NextToGoState.RaceSummary> = List(10) { nextRaceSummary().toRaceSummary() }.toImmutableList(),
) = NextToGoState.Data(
    raceSummaries = raceSummaries,
    localRaceSummaries = localRaceSummaries,
)
