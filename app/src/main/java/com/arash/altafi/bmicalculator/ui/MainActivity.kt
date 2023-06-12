package com.arash.altafi.bmicalculator.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.arash.altafi.bmicalculator.R
import com.arash.altafi.bmicalculator.databinding.ActivityMainBinding
import com.arash.altafi.bmicalculator.utils.isNightModeEnabled
import com.arash.altafi.bmicalculator.utils.toast

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var gender = "Male"
    private var height = 175
    private var weight = 75
    private var age = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.apply {
        if (isNightModeEnabled()) {
            lightMode.setImageResource(R.drawable.ic_light)
        } else {
            lightMode.setImageResource(R.drawable.ic_moon_dark)
        }

        lightMode.setOnClickListener {
            if (isNightModeEnabled()) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
                lightMode.setImageResource(R.drawable.ic_light)
            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
                lightMode.setImageResource(R.drawable.ic_moon_dark)
            }
        }

        maleCard.setOnClickListener {
            male.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorSecondary
                    )
                )

            female.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorPrimary
                    )
                )

            maleText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.lightColor
                )
            )

            maleIcon.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.lightColor
                )
            )

            femaleText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.black
                )
            )

            femaleIcon.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.black
                )
            )

            gender = "Male"
        }

        femaleCard.setOnClickListener {
            male.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorPrimary
                    )
                )

            maleText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.black
                )
            )

            maleIcon.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.black
                )
            )

            femaleText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.lightColor
                )
            )

            femaleIcon.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.lightColor
                )
            )

            female.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorSecondary
                    )
                )

            gender = "Female"
        }

        valueWeight.setOnClickListener {
            valueWeight.isCursorVisible = true
        }

        valueAge.setOnClickListener {
            valueAge.isCursorVisible = true
        }

        valueWeight.setOnEditorActionListener { _, actionId, _ ->
            valueWeight.isCursorVisible = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                valueWeight.isCursorVisible = false
                if (valueWeight.text.toString().isEmpty() || valueWeight.text.toString()
                        .toInt() == 0
                ) {
                    weight = 75
                    valueWeight.setText(weight.toString())
                } else {
                    weight = valueWeight.text.toString().toInt()
                }
            }
            false
        }

        valueAge.setOnEditorActionListener { _, actionId, _ ->
            valueAge.isCursorVisible = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                valueAge.isCursorVisible = false
                if (valueAge.text.toString().isEmpty() || valueAge.text.toString()
                        .toInt() == 0
                ) {
                    age = 25
                    valueAge.setText(age.toString())
                } else {
                    age = valueAge.text.toString().toInt()
                }
            }
            false
        }

        plusWeight.setOnClickListener {
            if (weight < 999) {
                weight += 1
                valueWeight.setText(weight.toString())
            }
        }

        minusWeight.setOnClickListener {
            if (weight > 1) {
                weight -= 1
                valueWeight.setText(weight.toString())
            }
        }

        plusAge.setOnClickListener {
            if (age < 999) {
                age += 1
                valueAge.setText(age.toString())
            }
        }

        minusAge.setOnClickListener {
            if (age > 1) {
                age -= 1
                valueAge.setText(age.toString())
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                height = progress
                valueHeight.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        calculateBmi.setOnClickListener {
            when {
                TextUtils.isEmpty(valueWeight.text.toString()) -> {
                    toast("Please enter correct Weight")
                }

                TextUtils.isEmpty(valueAge.text.toString()) -> {
                    toast("Please enter correct Age")
                }

                else -> {
                    age = valueAge.text.toString().toInt()
                    weight = valueWeight.text.toString().toInt()

                    val bmi = String.format("%.02f", bmiCalc(height.toFloat(), weight.toFloat()))

                    val intent = Intent(this@MainActivity, ResultsActivity::class.java)
                    intent.putExtra("BMI_VALUE", bmi)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }
        }

    }

    private fun bmiCalc(height: Float, weight: Float): Float {
        val heightInMeter = height / 100
        return weight / (heightInMeter * heightInMeter)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
