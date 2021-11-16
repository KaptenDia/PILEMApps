package com.emud.pilemapps.home.tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.emud.pilemapps.R
import com.emud.pilemapps.model.Checkout
import com.emud.pilemapps.model.Film
import kotlinx.android.synthetic.main.activity_tiket.*
import java.util.ArrayList

class TiketActivity : AppCompatActivity() {

    private var datalist =ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        var data= intent.getParcelableExtra<Film>("data")

        tv_title.text = data!!.judul
        tv_genre2.text = data!!.genre
        tv_rate2.text = data!!.rating

        Glide.with(this)
            .load(data.poster)
            .into((iv_poster_image))

        rc_checkout.layoutManager = LinearLayoutManager(this)
        datalist.add(Checkout("A3", ""))
        datalist.add(Checkout("B2", ""))

        rc_checkout.adapter = TiketAdapter(datalist){

        }
    }
}