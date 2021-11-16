package com.emud.pilemapps.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.emud.pilemapps.R
import android.widget.Button
import com.emud.pilemapps.sign.SignInActivity
import com.emud.pilemapps.utility.Preferences

class OnBoardingOneMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_one_main)
        lateinit var preferences : Preferences

        preferences = Preferences(this)

        if(preferences.getValue("onboarding").equals("1")){
            finishAffinity()

            var intent = Intent(this@OnBoardingOneMainActivity,SignInActivity::class.java)
            startActivity(intent)
        }

        var btnNext=findViewById<Button>(R.id.btn_lanjutkan) as Button
        btnNext.setOnClickListener {
            var intent = Intent(this@OnBoardingOneMainActivity, OnBoardingTwoMainActivity::class.java)
            startActivity(intent)
        }
        var btnSkip=findViewById<Button>(R.id.btn_lewati) as Button
        btnSkip.setOnClickListener {
            preferences.setValue("onboarding", "1")
            finishAffinity()

            var intent = Intent(this@OnBoardingOneMainActivity,SignInActivity::class.java)
            startActivity(intent)
        }
    }
}