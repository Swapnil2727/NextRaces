package com.example.nexttogo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

object StylesheetTypography {
    val Heading1 = HeadingTextStyle(fontSize = 18.sp)
    val Subtitle1 = BodyTextStyle(fontSize = 14.sp)
}

private fun HeadingTextStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.W600,
) = TextStyle(
    color = Navy1400,
    fontSize = fontSize,
    fontWeight = fontWeight,
)


private fun BodyTextStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.W400,
) = TextStyle(
    color = Navy1400,
    fontSize = fontSize,
    fontWeight = fontWeight,
)