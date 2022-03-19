package com.vinayakstudios.workoutapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.vinayakstudios.workoutapp.databinding.ActivityExerciseBinding
import com.vinayakstudios.workoutapp.databinding.ActivityMainBinding
import com.vinayakstudios.workoutapp.databinding.DialogCustomBackConirmationBinding
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

   private var binding: ActivityExerciseBinding? = null
   private var workoutTimer: CountDownTimer? = null
   private var timerDuration: Long = 1
   private var timerProgress = 0
   private var timerProgressMaxLimit = 10
   private var timerTitle: String? = null
   private var isExerciseTimerOn: Boolean = false

   private var exerciseList: ArrayList<ExerciseModel>? = null
   private var currentExercisePosition: Int = -1
   private var currentExercise: ExerciseModel? = null
   private var textToSpeech: TextToSpeech? = null
   private var player: MediaPlayer? = null
   private var startMusicPlayed: Boolean = false

   private var exerciseAdapter: ExerciseStatusAdapter? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityExerciseBinding.inflate(layoutInflater)
      setContentView(binding?.root)

      setSupportActionBar(binding?.exerciseToolBar)

      if (supportActionBar != null) {
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
      binding?.exerciseToolBar?.setNavigationOnClickListener {
         customDialogForBackBtn()
      }

      exerciseList = Constants.defaultExerciseList()
      textToSpeech = TextToSpeech(this, this)
      setupExerciseStatusRV()

      startRestTimer()
   }

   override fun onInit(status: Int) {

      if (status == TextToSpeech.SUCCESS) {
         val result = textToSpeech!!.setLanguage(Locale.UK)
         if (result == TextToSpeech.LANG_NOT_SUPPORTED
            || result == TextToSpeech.LANG_MISSING_DATA
         ) {
            Log.e("TTS", "The Language specified is not supported!")
         }
      } else {
         Log.e("TTS", "Text to Speech initialization failed!!")
      }
   }

   override fun onBackPressed() {
      customDialogForBackBtn()
   }

   private fun startTimer(){
      playStartMusic()
      binding?.pbTimer?.progress = timerProgress
      binding?.pbTimer?.max = timerProgressMaxLimit
      workoutTimer = object : CountDownTimer(timerDuration - 0, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            timerProgress++
            binding?.pbTimer?.progress = timerProgressMaxLimit - timerProgress
            binding?.tvTimer?.text = (timerProgressMaxLimit - timerProgress).toString()
         }

         @SuppressLint("NotifyDataSetChanged")
         override fun onFinish() {
            val exerciseListSize = exerciseList?.size
            if (isExerciseTimerOn) {

               exerciseList!![currentExercisePosition].setIsSelected(false)
               exerciseList!![currentExercisePosition].setIsCompleted(true)
               exerciseAdapter!!.notifyDataSetChanged()

               if(currentExercisePosition == exerciseListSize!! - 1){
                  val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                  startActivity(intent)
                  finish()
               }
               else{
                  startRestTimer()
               }
            } else {
               if(currentExercisePosition < 0)
               {
                  currentExercisePosition++
                  exerciseList!![currentExercisePosition].setIsSelected(true)
                  exerciseAdapter!!.notifyDataSetChanged()
                  startExercise(currentExercisePosition)
               }else if (exerciseListSize != null &&
                  currentExercisePosition < exerciseListSize-1)
                  {
                     currentExercisePosition++
                     exerciseList!![currentExercisePosition].setIsSelected(true)
                     exerciseAdapter!!.notifyDataSetChanged()
                     startExercise(currentExercisePosition)
               }
            }

         }
      }.start()
   }

   private fun startRestTimer() {
      isExerciseTimerOn = false
      timerTitle = "Get Ready For!!"
      binding?.tvTimerTitle?.text = timerTitle
      binding?.ivExerciseImage?.setImageResource(R.drawable.rest)

      var upcomingExercise = exerciseList!![currentExercisePosition + 1]
      binding?.tvUpcomingExerciseLabel?.visibility = View.VISIBLE
      binding?.tvUpcomingExercise?.visibility = View.VISIBLE
      binding?.tvUpcomingExercise?.text = upcomingExercise.getExerciseName()
      textToSpeechStart("Next Exercise is ${upcomingExercise.getExerciseName()}")
      timerDuration = 1000
      timerProgress = 0
      timerProgressMaxLimit = 10
      startTimer()
   }

   private fun setupRestView() {
      if (workoutTimer != null) {
         workoutTimer!!.cancel()
      }
      timerProgress = 0
   }

   private fun customDialogForBackBtn(){
      val customDialog = Dialog(this)
      val dialogBinding = DialogCustomBackConirmationBinding.inflate(layoutInflater)
      customDialog.setContentView(dialogBinding.root)
      customDialog.setCanceledOnTouchOutside(false)

      dialogBinding.btnDialogYes.setOnClickListener{
         this@ExerciseActivity.finish()
         customDialog.dismiss()
      }
      dialogBinding.btnDialogNo.setOnClickListener{
         customDialog.dismiss()
      }
      customDialog.show()
   }

   private fun startExercise(currentExercisePos: Int) {
      isExerciseTimerOn = true

      binding?.tvUpcomingExerciseLabel?.visibility = View.GONE
      binding?.tvUpcomingExercise?.visibility = View.GONE

      if (exerciseList != null) {
         currentExercise = exerciseList!![currentExercisePos]
      }
      //isExerciseTimerOn = true
      if (currentExercise != null) {
         timerTitle = currentExercise!!.getExerciseName()
         binding?.tvTimerTitle?.text = timerTitle
         binding?.ivExerciseImage?.setImageResource(currentExercise!!.getImage())
      }
      timerDuration = 1000
      timerProgress = 0
      timerProgressMaxLimit = 30

      startTimer()
      textToSpeechStart("Start ${currentExercise!!.getExerciseName()}")
   }

   private fun textToSpeechStart(text: String) {
      textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

   }

   private fun playStartMusic(){
      if(!startMusicPlayed){
         try{
            val soundUri : Uri = Uri.parse("android.resource://com.vinayakstudios.workoutapp/"
                    + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundUri)
            player?.isLooping = false
            player?.start()
            startMusicPlayed = true
         }catch(e: Exception){
            e.printStackTrace()
         }
      }
   }

   private fun setupExerciseStatusRV(){
      binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,
         LinearLayoutManager.HORIZONTAL,false)
      exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
      binding?.rvExerciseStatus?.adapter = exerciseAdapter
   }

      override fun onDestroy() {
         super.onDestroy()
         if (workoutTimer != null) {
            workoutTimer!!.cancel()
         }
         if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
         }
         if(player != null){
            player!!.stop()
         }
         timerProgress = 0
         binding = null
      }

}
