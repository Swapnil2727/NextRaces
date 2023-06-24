package com.example.nexttogo.ui.nexttogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.employeebook.utils.identifier
import com.example.employeebook.utils.loremIpsum
import com.example.nexttogo.R
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.ui.convertToRemainingTime
import com.example.nexttogo.ui.theme.Grey400
import com.example.nexttogo.utils.PlaceholderShape
import com.example.nexttogo.utils.autoPlaceholder
import com.example.nexttogo.utils.hideIfPlaceholder
import kotlinx.coroutines.delay

@Composable
fun RaceItem(
    raceId: String,
    meetingName: String,
    raceNumber: Int,
    startTime: Long,
    category: NextToGoState.RaceCategory,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(Grey400)
            .padding(12.dp)
            .semantics(mergeDescendants = true) { identifier = raceId },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val imageRes = when (category) {
            NextToGoState.RaceCategory.HORSE -> R.drawable.horse
            NextToGoState.RaceCategory.HARNESS -> R.drawable.harness
            NextToGoState.RaceCategory.GREYHOUND -> R.drawable.greyhound
        }
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(56.dp)
                .autoPlaceholder(PlaceholderShape.Icon)
                .testTag(category.name)
        )
        Text(
            text = raceNumber.toString(),
            modifier = Modifier
                .autoPlaceholder(PlaceholderShape.TextLarge)
                .testTag("raceNumber")
        )
        Text(
            text = meetingName,
            modifier = Modifier
                .autoPlaceholder(PlaceholderShape.TextSmall)
                .testTag("meetingName")
        )
        TimeToGoInfo(
            startTime = startTime,
            modifier = Modifier
                .hideIfPlaceholder()
                .testTag("remainingTimeToGo")
        )
    }
}

@Composable
fun TimeToGoInfo(
    startTime: Long,
    modifier: Modifier = Modifier
) {
    val remainingTime = remember { mutableStateOf("") }
    LaunchedEffect(startTime) {
        while (true) {
            val newRemainingTime = convertToRemainingTime(startTime)
            remainingTime.value = newRemainingTime
            delay(1000) // Update every second
        }
    }
    Text(
        text = remainingTime.value,
        modifier = modifier
    )
}


@Preview
@Composable
private fun RaceItemPreview() = RaceItem(
    raceId = loremIpsum(1),
    meetingName = loremIpsum(4),
    raceNumber = 8,
    startTime = 1686980040,
    category = NextToGoState.RaceCategory.HORSE,
)
