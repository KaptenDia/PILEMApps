package com.emud.pilemapps.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.emud.pilemapps.R
import com.emud.pilemapps.sign.SignInActivity
import com.emud.pilemapps.sign.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var sUsername:String
    private lateinit var sPassword:String
    private lateinit var sNama:String
    private lateinit var sEmail:String

    private lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabaseReference = mFirebaseInstance.getReference("user")

        val etview: EditText = findViewById(R.id.et_username)
        val etview2: EditText = findViewById(R.id.et_password)
        val etview3: EditText = findViewById(R.id.et_nama)
        val etview4: EditText = findViewById(R.id.et_email)

        val button = findViewById<Button>(R.id.btn_lanjutkan)
        button.setOnClickListener {
            sUsername = etview.text.toString()
            sPassword = etview2.text.toString()
            sNama = etview3.text.toString()
            sEmail = etview4.text.toString()

            when {
                sUsername == "" -> {
                    etview.error = "Silahkan Isi Username Anda"
                    etview.requestFocus()
                }
                sPassword == "" -> {
                    etview2.error = "Silahkan Isi Password Anda"
                    etview2.requestFocus()
                }
                sNama == "" -> {
                    etview3.error = "Silahkan Isi Nama Anda"
                    etview3.requestFocus()
                }
                sEmail == "" -> {
                    etview4.error = "Silahkan Isi Email Anda"
                    etview4.requestFocus()
                }
                else -> {
                    saveUsername (sUsername, sPassword , sNama,sEmail)
                }
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama
        user.email = sEmail

        if (sUsername != null) {
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    mDatabaseReference.child(sUsername).setValue(data)

                    val goSignUpPhotoscreen = Intent(this@SignUpActivity,
                        SignUpPhotoscreenActivity::class.java).putExtra("nama", data.nama)
                    startActivity(goSignUpPhotoscreen)

                }else {
                    Toast.makeText(this@SignUpActivity, "User Sudah Digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+databaseError.message,Toast.LENGTH_LONG).show()
            }

        })
    }
}