package com.vinayakstudios.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.vinayakstudios.workoutapp.databinding.ActivityHistoryPageBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryPageActivity : AppCompatActivity() {

   private var binding : ActivityHistoryPageBinding? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityHistoryPageBinding.inflate(layoutInflater)
      setContentView(binding?.root)
      setSupportActionBar(binding?.toolbarHistoryPageActivity)

      if(supportActionBar != null){
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.title = "HISTORY"
      }

      binding?.toolbarHistoryPageActivity?.setNavigationOnClickListener{
         onBackPressed()
      }

      val dao = (application as WorkoutApp).db?.historyDao()
      getAllCompletedDates(dao)

   }

   private fun getAllCompletedDates(historyDao : HistoryDao?){
      lifecycleScope.launch {
         historyDao?.fetchAllDates()?.collect { allCompletedDates ->
            val list = ArrayList(allCompletedDates)
            setUpDataInRecyclerView(list)
            }
         }
      }

   override fun onDestroy() {
      super.onDestroy()
      binding = null
   }

   private fun setUpDataInRecyclerView(historyList : ArrayList<HistoryEntity>){
      if(historyList.isNotEmpty()){
         val historyRvAdapter = HistoryRVAdapter(historyList)

         binding?.rvHistoryEntriesTitle?.visibility = View.VISIBLE
         binding?.rvHistoryEntries?.visibility = View.VISIBLE
         binding?.tvNoDataAvailable?.visibility = View.GONE
         binding?.rvHistoryEntries?.layoutManager = LinearLayoutManager(this)
         binding?.rvHistoryEntries?.adapter = historyRvAdapter
      }
      else{
         binding?.rvHistoryEntriesTitle?.visibility = View.GONE
         binding?.rvHistoryEntries?.visibility = View.GONE
         binding?.tvNoDataAvailable?.visibility = View.VISIBLE
      }
   }
}