package com.vinayakstudios.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinayakstudios.workoutapp.databinding.ActivityHistoryPageBinding
import com.vinayakstudios.workoutapp.databinding.HistoryPageRowBinding

class HistoryRVAdapter(private val historyList : ArrayList<HistoryEntity>)
   : RecyclerView.Adapter<HistoryRVAdapter.ViewHolder>() {

   class ViewHolder(binding: HistoryPageRowBinding):RecyclerView.ViewHolder(binding.root){
      val llHistoryListRowMain = binding.llHistoryListRow
      val tvPosition = binding.tvPosition
      val tvItem = binding.tvItem
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(HistoryPageRowBinding.inflate(LayoutInflater.from(parent.context),
         parent,false))
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = historyList[position]

      holder.tvItem.text = item.date

      val rvPosition = position + 1
      holder.tvPosition.text = rvPosition.toString()

      if(position % 2 == 0){
         holder.llHistoryListRowMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
      }
      else {
         holder.llHistoryListRowMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
      }
   }

   override fun getItemCount(): Int {
      return historyList.size
   }
}