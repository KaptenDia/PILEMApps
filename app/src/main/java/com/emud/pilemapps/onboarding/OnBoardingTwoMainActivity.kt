package com.emud.pilemapps.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.emud.pilemapps.R

class OnBoardingTwoMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_two_main)

        var btnNext=findViewById(R.id.btnlanjutkan) as Button
        btnNext.setOnClickListener {
            var intent = Intent(this@OnBoardingTwoMainActivity, OnBoardingThreeMainActivity::class.java)
            startActivity(intent)
        }
    }
}