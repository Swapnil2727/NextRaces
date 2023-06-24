package com.example.nexttogo.ui.nexttogo

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.employeebook.utils.loremIpsum
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.ui.convertToRemainingTime
import com.example.nexttogo.utils.nextInstant
import com.example.nexttogo.utils.nextRaceSummary
import com.example.nexttogo.utils.random
import com.example.nexttogo.utils.runAndroidComposeUiTest
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RaceItemTest {

    @Test
    fun `given meeting name, should match meeting name`() = runAndroidComposeUiTest {
        val meetingName = loremIpsum(2)
        setContent { Item(meetingName = meetingName) }
        onNode(isMeetingName(), true).assertTextContains(meetingName)
    }

    @Test
    fun `given category is greyhound, icon should match tag`() = runAndroidComposeUiTest {
        val category = NextToGoState.RaceCategory.GREYHOUND
        setContent { Item(category = category) }
        onNodeWithTag(category.name, true).assertExists()
    }

    @Test
    fun `given race number, should match race number text`() = runAndroidComposeUiTest {
        val raceNumber = random.nextInt()
        setContent { Item(raceNumber = raceNumber) }
        onNode(isRaceNumber(), true).assertTextContains(raceNumber.toString())
    }

    @Test
    fun `given start time in seconds, should match remaining time text`() =
        runAndroidComposeUiTest {
            val startTime = 1686980056L
            val remainingTime = convertToRemainingTime(startTime)
            setContent { Item(startTime = startTime) }
            onNode(isRemainingTimeToGo(), true).assertTextContains(remainingTime)
        }


    @Composable
    private fun Item(
        meetingName: String = random.nextRaceSummary().meetingName,
        raceNumber: Int = random.nextInt(),
        startTime: Long = random.nextInstant().epochSecond,
        category: NextToGoState.RaceCategory = NextToGoState.RaceCategory.HORSE
    ) = RaceItem(
        raceId = loremIpsum(1),
        meetingName = meetingName,
        raceNumber = raceNumber,
        startTime = startTime,
        category = category,
    )
}

private fun isMeetingName() = hasTestTag("meetingName")
private fun isRemainingTimeToGo() = hasTestTag("remainingTimeToGo")
private fun isRaceNumber() = hasTestTag("raceNumber")
