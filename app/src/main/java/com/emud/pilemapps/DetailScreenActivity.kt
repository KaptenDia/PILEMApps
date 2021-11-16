package com.emud.pilemapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.emud.pilemapps.checkout.PilihBangkuActivity
import com.emud.pilemapps.home.HomeActivity
import com.emud.pilemapps.home.dashboard.PlaysAdapter
import com.emud.pilemapps.model.Film
import com.emud.pilemapps.model.Plays
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_screen.*

class DetailScreenActivity : AppCompatActivity() {
    private  lateinit var mDatabase : DatabaseReference
    private  var datalist = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data!!.judul.toString())
            .child("Play")

        tv_kursi.text = data.judul
        tv_genre.text = data.genre
        tv_desc.text = data.desc
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        rv_who_played.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        getData()

        imageView7.setOnClickListener {
            val intent = Intent(this@DetailScreenActivity, HomeActivity::class.java)
                .putExtra("data",data)
            startActivity(intent)
        }

        btn_pilih_bangku.setOnClickListener {
            val intent = Intent(this@DetailScreenActivity, PilihBangkuActivity::class.java)
                .putExtra("data", data)
            startActivity(intent)
        }

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()

                for (getdataSnapshot in snapshot.children){
                    val Film = getdataSnapshot.getValue(Plays::class.java)
                    datalist.add(Film!!)
                }

                rv_who_played.adapter = PlaysAdapter(datalist){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailScreenActivity,""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}