package com.emud.pilemapps.sign
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emud.pilemapps.home.HomeActivity
import com.emud.pilemapps.R
import com.emud.pilemapps.sign.signup.SignUpActivity
import com.emud.pilemapps.utility.Preferences
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*


class SignInActivity : AppCompatActivity() {

    private lateinit var iUsername: String
    private lateinit var iPassword: String

    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        FirebaseApp.initializeApp(this)
        preferences = Preferences(this)

        preferences.setValue("onboarding", "1")
        if (preferences.getValue("status").equals("1")){
            finishAffinity()

            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        val etview: EditText = findViewById(R.id.et_username)
        val etview2: EditText = findViewById(R.id.et_password)

        val button = findViewById<Button>(R.id.button_masuk)
        button.setOnClickListener {
            iUsername = etview.text.toString()
            iPassword = etview2.text.toString()

            when {
                iUsername == "" -> {
                    etview.error = "Input Username Anda"
                    etview.requestFocus()
                }
                iPassword == "" -> {
                    etview2.error = "Input Password Anda"
                    etview2.requestFocus()
                }
                else -> {
                    pushLogin(iUsername, iPassword)
                }
            }
        }
        val button2 = findViewById<Button>(R.id.button_daftar)
        button2.setOnClickListener {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user == null){
                    Toast.makeText(this@SignInActivity,"Username Tidak Ditemukan",Toast.LENGTH_LONG).show()
                } else {
                    if(user.password.equals(iPassword)){

                        preferences.setValue("nama", user.nama.toString())
                        preferences.setValue("user", user.username.toString())
                        preferences.setValue("url", user.url.toString())
                        preferences.setValue("email", user.email.toString())
                        preferences.setValue("saldo", user.saldo.toString())
                        preferences.setValue("status", "1")

                    val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                        Toast.makeText(this@SignInActivity,"Password Anda Salah",Toast.LENGTH_LONG).show()
                }
            }
            
        }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity,error.message,Toast.LENGTH_LONG).show()
            }
        })
    }}