package com.example.employeebook.utils

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

fun loremIpsum(words: Int): String = LoremIpsum(words).values.first()
