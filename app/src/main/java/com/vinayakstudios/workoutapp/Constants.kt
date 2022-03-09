package com.vinayakstudios.workoutapp

object Constants {
   fun defaultExerciseList() : ArrayList<ExerciseModel>{
      val exerciseModelList = ArrayList<ExerciseModel>()

      val jumpingJacks = ExerciseModel(
         1,
         "Jumping Jacks",
         R.drawable.jumping_jacks,
         false,
         false
      )
      exerciseModelList.add(jumpingJacks)

      val pushUps = ExerciseModel(
         2,
         "Push Ups",
         R.drawable.push_ups,
         false,
         false
      )
      exerciseModelList.add(pushUps)

      val rLegDonkeyKick = ExerciseModel(
         3,
         "Right Leg Donkey Kick",
         R.drawable.right_leg_donkey_kick,
         false,
         false
      )
      exerciseModelList.add(rLegDonkeyKick)

      val lLegDonkeyKick = ExerciseModel(
         4,
         "Left Leg Donkey Kick",
         R.drawable.left_leg_donkey_kick,
         false,
         false
      )
      exerciseModelList.add(lLegDonkeyKick)

      val hammerRoll = ExerciseModel(
         5,
         "Hammer Roll",
         R.drawable.hammerroll,
         false,
         false
      )
      exerciseModelList.add(hammerRoll)

      val lunges = ExerciseModel(
         6,
         "Lunges",
         R.drawable.lunges,
         false,
         false
      )
      exerciseModelList.add(lunges)

      val skipping = ExerciseModel(
         7,
         "Skipping",
         R.drawable.skipping,
         false,
         false
      )
      exerciseModelList.add(skipping)

      return exerciseModelList
   }
}