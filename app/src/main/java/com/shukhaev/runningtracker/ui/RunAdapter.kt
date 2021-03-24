package com.shukhaev.runningtracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shukhaev.runningtracker.R
import com.shukhaev.runningtracker.db.Run
import com.shukhaev.runningtracker.utils.Util
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false)
        return RunViewHolder(view)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.bind(run)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(run: Run) {
            itemView.apply {
                Glide.with(this).load(run.image).into(ivRunImage)

                val calendar = Calendar.getInstance().apply {
                    timeInMillis = run.timeStamp
                }
                val dateFormat= SimpleDateFormat("dd.MM.yyyy",Locale.getDefault())
                tvDate.text = dateFormat.format(calendar.time)

                tvAvgSpeed.text = "${run.avgSpeedInKMH} km/h"
                tvDistance.text = "${run.distanceInMeters/1000f} km"
                tvTime.text = Util.getFormattedStopWatchTime(run.timeInMillis)
                tvCalories.text = "${run.caloriesBurned} kcal"
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

}