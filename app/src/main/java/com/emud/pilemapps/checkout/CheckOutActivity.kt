package com.emud.pilemapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.emud.pilemapps.R
import com.emud.pilemapps.model.Checkout
import com.emud.pilemapps.utility.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckOutActivity : AppCompatActivity() {

    private var datalist = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        datalist = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in datalist.indices){
            total += datalist[a].harga!!.toInt()
        }

        datalist.add(Checkout("Total Harus Dibayar", total.toString()))

        rc_checkout.layoutManager = LinearLayoutManager(this)
        rc_checkout.adapter = CheckoutAdapter(datalist){

        }

        imageView3.setOnClickListener {
            finishAffinity()

            var intent = Intent(this,PilihBangkuActivity::class.java)
            startActivity(intent)
        }


        btn_tiket.setOnClickListener {
            var intent = Intent(this, CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }
    }
}