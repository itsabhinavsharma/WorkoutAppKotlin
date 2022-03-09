package com.vinayakstudios.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.vinayakstudios.workoutapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

   private var binding : ActivityFinishBinding? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityFinishBinding.inflate(layoutInflater)
      setContentView(binding?.root)

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
}