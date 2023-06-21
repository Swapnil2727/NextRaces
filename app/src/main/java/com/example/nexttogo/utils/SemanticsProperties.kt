package com.example.employeebook.utils

import android.net.Uri
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import okhttp3.HttpUrl

val Identifier = SemanticsPropertyKey<String>("Identifier")
var SemanticsPropertyReceiver.identifier: String by Identifier

val ImageUrl: SemanticsPropertyKey<String?> = SemanticsPropertyKey("ImageUrl")
var SemanticsPropertyReceiver.imageUrl: String? by ImageUrl
