package com.emud.pilemapps.sign.signup

import android.Manifest.*
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emud.pilemapps.home.HomeActivity
import com.emud.pilemapps.R
import com.emud.pilemapps.utility.Preferences
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sin_up_photoscreen.*
import java.util.*


class SignUpPhotoscreenActivity : AppCompatActivity(),PermissionListener {

    private var statusAdd:Boolean = false
    private lateinit var filepath: Uri

    private lateinit var storage : FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var preferences: Preferences



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        tv_welcome.text = "Welcome, \n"+intent.getStringExtra("nama")

        iv_add.setOnClickListener{
            if(statusAdd){
                statusAdd = false
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_upload)
                iv_profile2.setImageResource(R.drawable.userpict)
            }else {
                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
                }
            }
        btn_home.setOnClickListener{
            finishAffinity()

            val goHome= Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_save.setOnClickListener{
            if(filepath != null){
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = storageReference.child("images"+ UUID.randomUUID().toString())
                ref.putFile(filepath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this,"Uploaded",Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener{
                            preferences.setValue("url",it.toString())
                        }

                        finishAffinity()
                        val goHome= Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
                        startActivity(goHome)
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload"+progress.toInt()+" %")
                    }
            }else {

            }
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        ImagePicker.with(this)
            .cameraOnly()
            .start()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this,"Anda Tidak Bisa Menambahkan Photo Profile",Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
    }

    override fun onBackPressed() {
        Toast.makeText(this,"Tergesa? Coba Tap Tombol Lewati Aja!",Toast.LENGTH_LONG).show()
    }
    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == Activity.RESULT_OK -> {
                statusAdd =true
                filepath = data?.data!!

                Glide.with(this)
                    .load(filepath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv_profile2)

                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_delete)
            }
            resultCode == ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

}