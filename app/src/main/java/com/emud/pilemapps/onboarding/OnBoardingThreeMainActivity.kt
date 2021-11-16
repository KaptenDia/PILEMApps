package com.emud.pilemapps.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.emud.pilemapps.R
import com.emud.pilemapps.sign.SignInActivity

class OnBoardingThreeMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_three_main)

        var btnStart=findViewById(R.id.btn_memulai) as Button
        btnStart.setOnClickListener {
            var intent =Intent(this@OnBoardingThreeMainActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}