package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidListItemBinding

class AsteroidListAdapter(
    val onCLickListener: OnCLickListener
):ListAdapter<Asteroid, AsteroidListAdapter.MyViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid,onCLickListener)
    }

    class MyViewHolder(private val binding: AsteroidListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Asteroid,onCLickListener: OnCLickListener){
            binding.asteroid = item
            binding.onClick = onCLickListener
        }
        companion object{
            fun from(parent:ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = AsteroidListItemBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(view)
            }
        }
    }

    class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>(){
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }

}