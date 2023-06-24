package com.example.nexttogo.ui.nexttogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.employeebook.utils.loremIpsum
import com.example.nexttogo.R
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.utils.EnablePlaceholders
import com.example.nexttogo.utils.ErrorNotice
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NextToGoScreen(
    data: NextToGoState.Data?,
    isLoading: Boolean,
    filteredByCategory: NextToGoState.RaceCategory?,
    error: NextToGoState.Error?,
    onRefresh: () -> Unit,
    onSelectCategory: (NextToGoState.RaceCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading && data != null,
        onRefresh = onRefresh
    )
    val scope = rememberCoroutineScope()
    val resources = LocalContext.current.resources
    var showMenu by remember { mutableStateOf(false) }
    val raceCategories = listOf(
        Pair(R.string.horse, NextToGoState.RaceCategory.HORSE),
        Pair(R.string.harness, NextToGoState.RaceCategory.HARNESS),
        Pair(R.string.grey_hound, NextToGoState.RaceCategory.GREYHOUND),
        Pair(R.string.all_races, null)
    )

    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                modifier = modifier,
                actions = {
                    if (data != null && error == null) {
                        IconButton(
                            onClick = { showMenu = !showMenu },
                            modifier = Modifier.testTag("menu")
                        ) {
                            Row {
                                Icon(imageVector = Icons.Default.List, contentDescription = null)
                                val filterTitle by remember(filteredByCategory) {
                                    mutableStateOf(
                                        filteredByCategory?.name
                                            ?: resources.getString(R.string.filter_by_category)
                                    )
                                }
                                Text(text = filterTitle)
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        raceCategories.forEach { (textResId, category) ->
                            DropdownMenuItem(
                                text = { Text(stringResource(textResId)) },
                                onClick = {
                                    if (category != null) {
                                        onSelectCategory(category)
                                    } else {
                                        onRefresh()
                                    }
                                    showMenu = false
                                }
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = modifier
                .pullRefresh(pullRefreshState)
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (data != null && !isLoading) {
                    items(
                        items = data.localRaceSummaries,
                        key = NextToGoState.RaceSummary::raceId
                    ) {
                        RaceItem(
                            raceId = it.raceId,
                            meetingName = it.meetingName,
                            raceNumber = it.raceNumber,
                            category = it.category,
                            startTime = it.startTime,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                    }
                }
                if (isLoading) {
                    items(items = PlaceholderData.raceSummaries) {
                        EnablePlaceholders {
                            RaceItem(
                                raceId = it.raceId,
                                meetingName = it.meetingName,
                                raceNumber = it.raceNumber,
                                startTime = it.startTime,
                                category = it.category,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
                if (data != null && data.localRaceSummaries.isEmpty() && !isLoading && error == null) {
                    item {
                        ErrorNotice(
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.no_race_flag),
                                    contentDescription = null
                                )
                            },
                            title = stringResource(R.string.no_races),
                            message = stringResource(R.string.please_visit_again_sometime_later),
                            buttonLabel = stringResource(R.string.refresh),
                            onButtonClick = onRefresh,
                            modifier = Modifier.testTag("EmptyNotice")
                        )
                    }
                }
                if (data == null && error != null) {
                    item {
                        ErrorNotice(
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.error_face),
                                    contentDescription = null
                                )
                            },
                            title = stringResource(R.string.failed_to_load),
                            message = stringResource(R.string.something_went_wrong_please_try_again),
                            buttonLabel = stringResource(R.string.refresh),
                            onButtonClick = onRefresh,
                            modifier = Modifier.testTag("ErrorNotice")
                        )
                    }
                }
                if (error != null && data != null) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            resources.getString(R.string.something_went_wrong_please_try_again)
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isLoading && data != null,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}


private val PlaceholderData = NextToGoState.Data(
    raceSummaries = List(5) { index ->
        NextToGoState.RaceSummary(
            raceId = "$index",
            raceName = loremIpsum(3),
            raceNumber = index,
            meetingName = loremIpsum(4),
            category = NextToGoState.RaceCategory.HARNESS,
            startTime = 1686980040
        )
    }.toPersistentList(),
    localRaceSummaries = persistentListOf()
)

private val Error = NextToGoState.Error(Exception())

@Preview
@Composable
private fun NextToGoScreenPreview() = NextToGoScreen(
    data = PlaceholderData,
    isLoading = false,
    error = null,
    onRefresh = {},
    onSelectCategory = {},
    filteredByCategory = null,
)

@Preview
@Composable
private fun Loading() = NextToGoScreen(
    data = null,
    isLoading = true,
    filteredByCategory = null,
    error = null,
    onRefresh = {},
    onSelectCategory = {},
)

@Preview
@Composable
private fun Error() = NextToGoScreen(
    data = null,
    isLoading = false,
    filteredByCategory = null,
    error = Error,
    onRefresh = {},
    onSelectCategory = {},
)

@Preview
@Composable
private fun Empty() = NextToGoScreen(
    data = null,
    isLoading = false,
    filteredByCategory = null,
    error = null,
    onRefresh = {},
    onSelectCategory = {},
)
