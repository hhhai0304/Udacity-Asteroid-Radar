package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemBinding
import com.udacity.asteroidradar.model.Asteroid

class MainAdapter(private val onClickListener: (it: Asteroid) -> Unit) :
    ListAdapter<Asteroid, MainAdapter.AsteroidViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class AsteroidViewHolder(private var binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder =
        AsteroidViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)

        holder.also {
            it.itemView.setOnClickListener {
                onClickListener(asteroid)
            }
            it.bind(asteroid)
        }
    }
}