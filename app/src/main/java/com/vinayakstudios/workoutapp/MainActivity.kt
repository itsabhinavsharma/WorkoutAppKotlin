package com.vinayakstudios.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.vinayakstudios.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private var binding : ActivityMainBinding? = null


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding?.root)


      startButtonOnClick()
      bmiButtonOnClick()
   }

   private fun startButtonOnClick() {
      binding?.flStartBtn?.setOnClickListener {
         val intent = Intent(this, ExerciseActivity::class.java)
         startActivity(intent)
      }
   }

   private fun bmiButtonOnClick(){
      binding?.flBmiBtn?.setOnClickListener{
         val intent  = Intent(this,CalculateBmiActivity::class.java)
         startActivity(intent)
      }
   }
}