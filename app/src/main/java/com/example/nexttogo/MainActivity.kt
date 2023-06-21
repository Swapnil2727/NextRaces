package com.example.nexttogo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexttogo.ui.nexttogo.NextToGoScreen
import com.example.nexttogo.ui.nexttogo.NextToGoViewModel
import com.example.nexttogo.ui.theme.NextToGoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<NextToGoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            Log.d("NextToGoViewModel", "${state}")
            NextToGoTheme {
                NextToGoScreen(
                    data = state.data,
                    isLoading = state.isLoading,
                    error = state.error,
                    onRefresh = viewModel::refresh,
                    onSelectCategory = viewModel::filterByCategory,
                    filteredByCategory = state.filteredByCategory
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NextToGoTheme {
        Greeting("Android")
    }
}