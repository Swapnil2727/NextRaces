package com.example.nexttogo.ui.nexttogo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nexttogo.data.remote.RaceApi
import com.example.nexttogo.data.repo.RealRaceService
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.utils.nextRaceSummary
import com.example.nexttogo.utils.random
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NextToGoViewModelTest {

    private lateinit var viewModel: NextToGoViewModel
    private val raceApi: RaceApi = mockk(relaxed = true)
    private val raceService = RealRaceService(raceApi)


    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = NextToGoViewModel(raceService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initially, should be loading`() {
        viewModel.refresh()
        viewModel.state.value shouldBe NextToGoState(isLoading = true)
    }

    @Test
    fun `initially, data should be null`() {
        viewModel.refresh()
        viewModel.state.value shouldBe NextToGoState(data = null)
    }

    @Test
    fun `initially, error should be null`() {
        viewModel.refresh()
        viewModel.state.value shouldBe NextToGoState(error = null)
    }


    @Test
    fun `given error loading races, should not be loading`() {
        coEvery { raceApi.allRaces() } throws Exception()
        viewModel.state.value.isLoading shouldBe false
    }


    @Test
    fun `given all races, when filter by horse category, localRaceSummaries state should only contain horse races`() {
        coEvery { raceApi.allRaces() } coAnswers {
            com.example.nexttogo.data.remote.AllRaceDetails(
                raceData = com.example.nexttogo.data.remote.AllRaceDetails.RaceData(
                    raceSummaries = mapOf(
                        "123" to random.nextRaceSummary()
                    )
                )
            )
        }
      //  viewModel.refresh()
        viewModel.filterByCategory(NextToGoState.RaceCategory.HORSE)
        viewModel.state.value.data?.localRaceSummaries shouldBe persistentListOf()
        viewModel.state.value.filteredByCategory shouldBe NextToGoState.RaceCategory.HORSE
    }

//    @Test
//    fun `given all races, when filter by horse category, localRaceSummaries state should only contain horse races`() {
//        coEvery { raceService.getAllRaces() } coAnswers {
//            flow {
//                AllRaceDetails(
//                    data = RaceData(raceSummaries = mapOf("123" to random.nextRaceSummary()))
//                )
//            }
//        }
//        viewModel.refresh()
//        viewModel.filterByCategory(NextToGoState.RaceCategory.HORSE)
//        viewModel.state.value.filteredByCategory shouldBe NextToGoState.RaceCategory.HORSE
//        viewModel.state.value.data?.localRaceSummaries shouldBe NextToGoState.RaceCategory.HORSE
//    }
}
