package com.example.golfperf.players.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerDetailFragment : Fragment() {

    private val viewModel: PlayerDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        arguments?.getString("playerId")?.let { viewModel.setPlayer(it) }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    PlayerDetailScreen(uiState = viewModel.uiState)
                }
            }
        }
    }
}
