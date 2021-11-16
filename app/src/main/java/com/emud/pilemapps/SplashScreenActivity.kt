package com.emud.pilemapps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.emud.pilemapps.onboarding.OnBoardingOneMainActivity

/*
    Ini Adalah Activity Pertama Yang Akan Dirun
    Tidak Ada Fitur Baru Atau Spesial Disini
    Hanya Melakukan Pending Time Beberapa
    Detik Saja.
 */

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

Handler(Looper.getMainLooper()).postDelayed({
    val intent = Intent(this@SplashScreenActivity, OnBoardingOneMainActivity::class.java)
    startActivity(intent)
    finish()
},3000)

        }
    }