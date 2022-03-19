package com.vinayakstudios.workoutapp

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.vinayakstudios.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

   private var binding : ActivityFinishBinding? = null
   private var historyDao : HistoryDao? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityFinishBinding.inflate(layoutInflater)
      setContentView(binding?.root)
      historyDao = (application as WorkoutApp).db?.historyDao()
      addDateToDatabase(historyDao!!)

      setSupportActionBar(binding?.exerciseToolBar)

      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
      binding?.exerciseToolBar?.setNavigationOnClickListener {
         onBackPressed()
      }

      finishButtonOnClick()
   }

   private fun finishButtonOnClick(){
      binding?.btnFinish!!.setOnClickListener{
         val intent = Intent(this@FinishActivity,MainActivity::class.java)
         startActivity(intent)
         finish()
      }
   }

   private fun addDateToDatabase(historyDao: HistoryDao){
      val myCalender = Calendar.getInstance()
      val dateTime = myCalender.time

      val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
      val date = sdf.format(dateTime)
      lifecycleScope.launch{
         historyDao.insert(HistoryEntity(date))
      }
   }
}