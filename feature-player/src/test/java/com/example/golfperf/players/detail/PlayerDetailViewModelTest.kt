package com.example.golfperf.players.detail

import app.cash.turbine.test
import com.example.domain.model.Player
import com.example.domain.model.Shot
import com.example.domain.repository.GolfRepository
import com.example.golfperf.domain.usecase.GetShotsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private class FakeGolfRepository(private val shots: List<Shot>) : GolfRepository {
        override fun observePlayers(): Flow<List<Player>> = flowOf(emptyList())
        override fun observeShots(playerId: String): Flow<List<Shot>> = flowOf(shots)
        override suspend fun refreshPLayers() = Unit
        override suspend fun refreshShots() = Unit
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits averaged stats once a player is selected`() = runTest {
        val shots = listOf(
            Shot(1, 1, ballSpeed = 100.0, launchAngle = 10.0, carryDistance = 200.0, clubType = "Driver", spinRate = 2000),
            Shot(2, 1, ballSpeed = 200.0, launchAngle = 20.0, carryDistance = 300.0, clubType = "Driver", spinRate = 2500),
        )
        val viewModel = PlayerDetailViewModel(GetShotsUseCase(FakeGolfRepository(shots)))

        viewModel.uiState.test {
            assertEquals(DetailUiState(), awaitItem())

            viewModel.setPlayer("1")

            val loaded = awaitItem()
            assertEquals(2, loaded.shots.size)
            assertEquals(150.0, loaded.avgSpeed, 0.001)
            assertEquals(250.0, loaded.avgDistance, 0.001)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
