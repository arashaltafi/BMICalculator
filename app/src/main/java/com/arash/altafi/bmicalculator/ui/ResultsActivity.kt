package com.arash.altafi.bmicalculator.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.arash.altafi.bmicalculator.R
import com.arash.altafi.bmicalculator.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResultsBinding.inflate(layoutInflater)
    }
    private var bmiValue = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.apply {
        val bmi = intent.getStringExtra("BMI_VALUE")
        if (bmi != null) {
            bmiValue = bmi.toFloat()
        }

        back.setOnClickListener {
            finish()
        }

        progress.max = 25
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progress.setProgress(bmiValue.toInt(), true)
        } else {
            progress.progress = bmiValue.toInt()
        }

        tvBmiValue.text = bmiValue.toString()
        tvBmiStatus.text = interpretBMI(bmiValue)

        showDetails.setOnClickListener {
            val intent = Intent(this@ResultsActivity, DetailsActivity::class.java)
            intent.putExtra("BMI_VALUE", bmiValue)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun interpretBMI(bmiValue: Float): String {
        binding.apply {
            if (bmiValue < 16) {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.severeUnderWeight
                        )
                    )
                return "You are Severely Underweight"
            } else if (bmiValue < 18.5) {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.underWeight
                        )
                    )
                return "You are Underweight"
            } else if (bmiValue < 25) {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.normalWeight
                        )
                    )
                return "You have Normal Body Weight"
            } else if (bmiValue < 30) {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.md_amber_600
                        )
                    )
                return "You are Overweight"
            } else if (bmiValue < 35) {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.obesityClass1
                        )
                    )
                return "You have Obesity Class I"
            } else if (bmiValue < 40) {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.obesityClass2
                        )
                    )
                return "You have Obesity Class II"
            } else {
                progress.progressTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@ResultsActivity,
                            R.color.obesityClass3
                        )
                    )
                return "You have Obesity Class III"
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}
