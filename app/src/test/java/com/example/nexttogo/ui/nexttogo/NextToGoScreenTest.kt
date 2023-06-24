package com.example.nexttogo.ui.nexttogo

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nexttogo.R
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.ui.NextToGoState.RaceCategory
import com.example.nexttogo.utils.assertSnackbarDisplayed
import com.example.nexttogo.utils.isList
import com.example.nexttogo.utils.nextData
import com.example.nexttogo.utils.random
import com.example.nexttogo.utils.resources
import com.example.nexttogo.utils.runAndroidComposeUiTest
import io.mockk.mockk
import io.mockk.verify
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NextToGoScreenTest {

    private val onRefresh: () -> Unit = mockk(relaxed = true)
    private val onSelectCategory: (RaceCategory) -> Unit = mockk(relaxed = true)

    @Test
    fun `screen title should be next to go`() {
        runAndroidComposeUiTest {
            setContent { Screen() }
            onNodeWithText(resources.getString(R.string.app_name)).assertExists()
        }
    }

    @Test
    fun `given data, each race item should be present`() {
        val data = random.nextData()
        runAndroidComposeUiTest {
            setContent { Screen(data = data) }
            data.localRaceSummaries.forEach {
                onNode(isList()).performScrollToKey(it.raceId)
            }
        }
    }

    @Test
    fun `given data, and no filter, menu title should be filter by category`() =
        runAndroidComposeUiTest {
            setContent { Screen(data = random.nextData(), filteredByCategory = null) }
            onNode(isMenu()).assertTextContains(resources.getString(R.string.filter_by_category))
        }

    @Test
    fun `given data, and filter by harness, menu title should be harness`() =
        runAndroidComposeUiTest {
            setContent {
                Screen(
                    data = random.nextData(),
                    filteredByCategory = RaceCategory.HARNESS
                )
            }
            onNode(isMenu()).assertTextContains(RaceCategory.HARNESS.name)
        }

    @Test
    fun `given all races clicked from menu item, should invoke refresh callback`() {
        runAndroidComposeUiTest {
            setContent { Screen() }
            onNode(isMenu()).performClick()
            onNodeWithText(resources.getString(R.string.all_races)).performClick()
            verify(exactly = 1) { onRefresh() }
        }
    }

    @Test
    fun `given greyhound clicked from menu item, should invoke select category callback`() {
        runAndroidComposeUiTest {
            setContent { Screen() }
            onNode(isMenu()).performClick()
            onNodeWithText(resources.getString(R.string.grey_hound)).performClick()
            verify(exactly = 1) { onSelectCategory(RaceCategory.GREYHOUND) }
        }
    }

    @Test
    fun `given error, not loading and null data, should present error notice`() {
        runAndroidComposeUiTest {
            setContent { Screen(data = null, isLoading = false, error = Error) }
            onNode(hasTestTag("ErrorNotice")).assertExists()
        }
    }

    @Test
    fun `given non empty data, empty local race summaries and no error, should present empty notice`() {
        runAndroidComposeUiTest {
            setContent {
                Screen(
                    data = random.nextData(localRaceSummaries = persistentListOf()),
                    error = null
                )
            }
            onNode(hasTestTag("EmptyNotice")).assertExists()
        }
    }

    @Test
    fun `given data loaded, and error occured, should present snackbar`() {
        runAndroidComposeUiTest {
            setContent { Screen(data = random.nextData(), error = Error) }
            assertSnackbarDisplayed(resources.getString(R.string.something_went_wrong_please_try_again))
        }
    }

    @Composable
    fun Screen(
        data: NextToGoState.Data? = random.nextData(),
        isLoading: Boolean = false,
        error: NextToGoState.Error? = null,
        filteredByCategory: RaceCategory? = null
    ) = NextToGoScreen(
        data = data,
        isLoading = isLoading,
        error = error,
        filteredByCategory = filteredByCategory,
        onRefresh = onRefresh,
        onSelectCategory = onSelectCategory,
    )
}

fun isMenu() = hasTestTag("menu")

private val Error = NextToGoState.Error(Exception())
