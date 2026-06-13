package com.example.golfperf.players.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feature_player.databinding.ItemPlayerBinding
import com.example.golfperf.domain.model.Player

class PlayersAdapter(
    private val onClick: (Player) -> Unit,
) : ListAdapter<Player, PlayersAdapter.PlayerViewHolder>(PlayerDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return PlayerViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlayerViewHolder(
        private val binding: ItemPlayerBinding,
        private val onClick: (Player) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
            binding.player = player
            binding.executePendingBindings()
            Glide.with(binding.avatar)
                .load(player.imageUrl)
                .circleCrop()
                .into(binding.avatar)
            binding.root.setOnClickListener { onClick(player) }
        }
    }

    private object PlayerDiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem == newItem
    }
}
