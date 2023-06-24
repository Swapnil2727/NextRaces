package com.example.nexttogo.ui.nexttogo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nexttogo.data.repo.RaceData
import com.example.nexttogo.data.repo.RaceService
import com.example.nexttogo.data.repo.RaceSummary
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.utils.nextRaceSummary
import com.example.nexttogo.utils.random
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NextToGoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: NextToGoViewModel

    private val raceService = mockk<RaceService>(relaxed = true)

    @Before
    fun setUp() {
        coEvery { raceService.getAllRaces() } coAnswers {
            flow {
                com.example.nexttogo.data.repo.AllRaceDetails(
                    data = RaceData(
                        raceSummaries = mapOf(
                            "123" to random.nextRaceSummary(
                                category = RaceSummary.RaceCategory.HORSE
                            )
                        )
                    )
                )
            }
        }
        viewModel = NextToGoViewModel(raceService)
    }

    @Test
    fun `initially, should be loading`() {
        viewModel.state.value shouldBe NextToGoState(isLoading = true)
    }

    @Test
    fun `initially, data should be null`() {
        viewModel.state.value shouldBe NextToGoState(data = null)
    }

    @Test
    fun `initially, error should be null`() {
        viewModel.state.value shouldBe NextToGoState(error = null)
    }

    @Test
    fun `given error loading races, should not be loading`() {
        coEvery { raceService.getAllRaces() } throws Exception()
        viewModel = NextToGoViewModel(raceService)
        viewModel.state.value.isLoading shouldBe false
    }

    @Test
    fun `given all races, when filter by horse category, filtered category should match`() =
        runTest {
            viewModel = NextToGoViewModel(raceService)
            viewModel.filterByCategory(NextToGoState.RaceCategory.HORSE)
            viewModel.state.value.filteredByCategory shouldBe NextToGoState.RaceCategory.HORSE
        }
}

// Taken from https://developer.android.com/kotlin/coroutines/test
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
