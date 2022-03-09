package com.vinayakstudios.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinayakstudios.workoutapp.databinding.ActivityCalculateBmiBinding

class CalculateBmiActivity : AppCompatActivity() {

   private var binding : ActivityCalculateBmiBinding? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityCalculateBmiBinding.inflate(layoutInflater)
      setContentView(binding?.root)
      setSupportActionBar(binding?.toolBarBmiActivity)

      if(supportActionBar != null){
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         supportActionBar?.title = "CALCULATE EMI"
      }

      binding?.toolBarBmiActivity?.setNavigationOnClickListener{
         onBackPressed()
      }
   }
}