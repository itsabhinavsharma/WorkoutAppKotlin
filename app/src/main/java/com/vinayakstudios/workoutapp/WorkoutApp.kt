package com.vinayakstudios.workoutapp

import android.app.Application

class WorkoutApp : Application() {
   val db by lazy{
      HistoryDatabase.getInstance(this)
   }
}