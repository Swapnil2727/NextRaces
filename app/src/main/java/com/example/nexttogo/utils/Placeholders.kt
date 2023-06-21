package com.example.nexttogo.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun isPlaceholder(): Boolean = LocalPlaceholderState.current

fun Modifier.autoPlaceholder(shape: Shape): Modifier = composed {
    placeholder(
        visible = isPlaceholder(),
        color = Color(0xFFEDEDED),
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(
            highlightColor = Color.White,
        ),
    )
}

@Suppress("ModifierMissing")
@Composable
fun EnablePlaceholders(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalPlaceholderState provides true) {
        Box(modifier = Modifier) {
            content()
        }
    }
}

inline fun Modifier.ifPlaceholder(crossinline factory: @Composable Modifier.() -> Modifier): Modifier = composed {
    if (isPlaceholder()) factory() else this
}

inline fun Modifier.hideIfPlaceholder(): Modifier = ifPlaceholder { alpha(0f) }

object PlaceholderShape {
    private val Default: Shape = RoundedCornerShape(percent = 50)
    val TextSmall: Shape = Default
    val TextLarge: Shape = Default
    val Icon: Shape = RoundedCornerShape(6.dp)
}

internal val LocalPlaceholderState: ProvidableCompositionLocal<Boolean> =
    compositionLocalOf { false }
