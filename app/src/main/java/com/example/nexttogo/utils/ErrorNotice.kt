package com.example.nexttogo.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.employeebook.utils.loremIpsum
import com.example.nexttogo.R
import com.example.nexttogo.ui.theme.StylesheetTypography

@Composable
fun ErrorNotice(
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    buttonLabel: String? = null,
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        icon()
        Spacer(modifier = Modifier.height(24.dp))
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = StylesheetTypography.Heading1,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (!message.isNullOrBlank()) {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = StylesheetTypography.Subtitle1,
            )
        }
        if (!buttonLabel.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onButtonClick,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(buttonLabel)
            }
        }
    }
}

@Preview
@Composable
private fun EmptyNoticePreview() = ErrorNotice(
    icon = {
        Image(
            painterResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
    },
    modifier = Modifier.fillMaxSize(),
    title = loremIpsum(3),
    message = loremIpsum(50),
    buttonLabel = loremIpsum(3),
)
