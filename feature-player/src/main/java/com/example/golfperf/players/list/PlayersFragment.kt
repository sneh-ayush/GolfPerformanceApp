package com.example.golfperf.players.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_player.R
import com.example.feature_player.databinding.FragmentPlayersBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayersViewModel by viewModel()

    private lateinit var adapter: PlayersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlayersAdapter { player ->
            findNavController().navigate(
                R.id.playerDetailFragment,
                bundleOf("playerId" to player.id.toString()),
            )
        }

        binding.playerslist.layoutManager = LinearLayoutManager(requireContext())
        binding.playerslist.adapter = adapter

        binding.searchInput.doAfterTextChanged { text ->
            viewModel.onSearch(text?.toString().orEmpty())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun render(state: PlayersUiState) {
        adapter.submitList(state.players)

        binding.progressBar.isVisible = state.isLoading
        binding.playerslist.isVisible = !state.isLoading

        val isEmpty = !state.isLoading && state.players.isEmpty()
        binding.statusContainer.isVisible = isEmpty
        if (isEmpty) {
            binding.statusText.text = state.errorMessage
                ?.let { getString(R.string.players_error, it) }
                ?: getString(R.string.players_empty)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
