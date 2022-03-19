package com.vinayakstudios.workoutapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.vinayakstudios.workoutapp.databinding.ActivityCalculateBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class CalculateBmiActivity : AppCompatActivity() {

   private var binding : ActivityCalculateBmiBinding? = null
   private var bmi = 0f
   private var bmiDescription : String? = null
   private var longAnimationDuration : Int? = null

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

      longAnimationDuration = resources.getInteger(android.R.integer.config_longAnimTime)

      calculateBtnOnClick()
      rgUnitsOnStateChanged()
   }

   private fun calculateBtnOnClick(){
      binding?.btnCalculate?.setOnClickListener {
         if(binding?.rbMetricUnits!!.isChecked){
            if(validateMetricEditTexts()){
               val Bmi = calculateMetricBmi()
               val BmiValue = BigDecimal(Bmi.toDouble())
                  .setScale(2,RoundingMode.HALF_EVEN).toString()
               val BmiType = checkBmiType(Bmi)
               val BmiDescription = bmiDescription

               binding?.tvBmiValue?.text = BmiValue
               binding?.tvBmiType?.text = BmiType
               binding?.tvBmiDescription?.text = BmiDescription
               binding?.llResultBmi?.visibility = View.VISIBLE
            }
            else{
               Toast.makeText(this,
                  "Enter Valid Values",
                  Toast.LENGTH_SHORT).show()
            }
         }
         else if(binding?.rbUsUnits!!.isChecked){
            if(validateUsEditTexts()){
               val Bmi = calculateUsBmi()
               val BmiValue = BigDecimal(Bmi.toDouble())
                  .setScale(2,RoundingMode.HALF_EVEN).toString()
               val BmiType = checkBmiType(Bmi)
               val BmiDescription = bmiDescription

               binding?.tvBmiValue?.text = BmiValue
               binding?.tvBmiType?.text = BmiType
               binding?.tvBmiDescription?.text = BmiDescription
               binding?.llResultBmi?.visibility = View.VISIBLE
            }
            else{
               Toast.makeText(this,
                  "Enter Valid Values",
                  Toast.LENGTH_SHORT).show()
            }
         }

      }
   }

   private fun validateMetricEditTexts() : Boolean{
      var isValid = true
      if(binding?.etMetricWeightInput?.text.toString().isEmpty()){
         isValid = false
      }
      else if(binding?.etHeightInput?.text.toString().isEmpty()){
         isValid = false
      }

      return isValid
   }

   private fun validateUsEditTexts() : Boolean{
      var isValid = true
      if(binding?.etUsWeightInput?.text.toString().isEmpty()){
         isValid = false
      }
      else if(binding?.etUsHeightFeet?.text.toString().isEmpty()){
         isValid = false
      }
      else if(binding?.etUsHeightInches?.text.toString().isEmpty()){
         isValid = false
      }

      return isValid
   }

   private fun calculateMetricBmi() : Float{
      val weight = binding?.etMetricWeightInput?.text.toString().toFloat()
      val height = binding?.etHeightInput?.text.toString().toFloat()/100

      val bmi = weight/(height * height)

      return bmi
   }

   private fun calculateUsBmi() : Float{
      val weight = binding?.etUsWeightInput?.text.toString().toFloat()
      val heightFeet = binding?.etUsHeightFeet?.text.toString().toFloat()
      val heightFeetInInches = heightFeet * 12f
      val heightInches = binding?.etUsHeightInches?.text.toString().toFloat()
      val totalInches = heightFeetInInches + heightInches

      val bmi = (weight/(totalInches * totalInches))*703

      return bmi
   }

   private fun checkBmiType(bmi: Float):String{
      var bmiType = "Normal"

      if(bmi < 15f){
         bmiType = "Very Severely UnderWeight"
         bmiDescription = "Oops! You Really need to take care of yourself! Eat More!!"
      }
      else if(bmi in 15f .. 16f){
         bmiType = "Severely UnderWeight"
         bmiDescription = "Oops! You Really need to take care of yourself! Eat More!!"
      }
      else if(bmi in 16f .. 18.5f){
         bmiType = "UnderWeight"
         bmiDescription = "Oops! You Really need to take care of yourself! Eat More!!"
      }
      else if(bmi in 18.5f..24.9f){
         bmiType = "Normal"
         bmiDescription = "Congratulations! You are in good Shape"
      }
      else if(bmi in 25.0f..29.9f){
         bmiType = "OverWeight"
         bmiDescription = "Oops! You Really need to take care of yourself! Workout More!!"
      }
      else if(bmi in 30f .. 35f){
         bmiType = "Moderately Obese"
         bmiDescription = "Oops! You Really need to take care of yourself! Workout More!!"
      }
      else if(bmi in 35f .. 40f){
         bmiType = "Severely Obese"
         bmiDescription = "OMG! You are in dangerous condition!! Act Now!!"
      }
      else{
         bmiType = "Very Severely Obese"
         bmiDescription = "OMG! You are in dangerous condition!! Act Now!!"
      }

      return bmiType
   }

   private fun rgUnitsOnStateChanged(){
      binding?.rgUnits?.setOnCheckedChangeListener { _,checkedId :Int ->

         if(checkedId == R.id.rbMetricUnits){
            if(binding?.etUsHeightFeetLayout?.visibility == View.VISIBLE ||
               binding?.etUsHeightInchLayout?.visibility == View.VISIBLE ||
                    binding?.etUsWeightInput?.visibility == View.VISIBLE){

                  viewOutAnimation(binding?.etUsWeightInputLayout!!)
                  viewOutAnimation(binding?.etUsHeightFeetLayout!!)
                  viewOutAnimation(binding?.etUsHeightInchLayout!!)
                  viewInAnimation(binding?.etMetricWeightInputLayout!!)
                  viewInAnimation(binding?.etHeightInputLayout!!)

                  binding?.llResultBmi?.visibility = View.INVISIBLE

                  val params = binding?.llResultBmi?.layoutParams as ConstraintLayout.LayoutParams
                  params.topToBottom = binding?.etHeightInputLayout!!.id
                  binding?.llResultBmi!!.requestLayout()

            }
         }
         else if(checkedId == R.id.rbUsUnits){
            if(binding?.etHeightInputLayout?.visibility == View.VISIBLE ||
                    binding?.etMetricWeightInputLayout?.visibility == View.VISIBLE) {
               viewOutAnimation(binding?.etMetricWeightInputLayout!!)
               viewOutAnimation(binding?.etHeightInputLayout!!)
               viewInAnimation(binding?.etUsWeightInputLayout!!)
               viewInAnimation(binding?.etUsHeightFeetLayout!!)
               viewInAnimation(binding?.etUsHeightInchLayout!!)

               binding?.llResultBmi?.visibility = View.INVISIBLE

               val params = binding?.llResultBmi?.layoutParams as ConstraintLayout.LayoutParams
               params.topToBottom = binding?.etUsHeightFeetLayout!!.id
               binding?.llResultBmi!!.requestLayout()
            }
         }

      }
   }

   private fun viewInAnimation(view : View){
      view.apply {
         alpha = 0f
         visibility = View.VISIBLE

         animate()
            .alpha(1f).duration = longAnimationDuration!!.toLong()
      }
   }

   private fun viewOutAnimation(view : View){
      view.apply {
         animate()
            .alpha(0f).duration = longAnimationDuration!!.toLong()
         visibility = View.GONE
      }
   }
}