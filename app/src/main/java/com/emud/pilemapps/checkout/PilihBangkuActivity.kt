package com.emud.pilemapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.emud.pilemapps.DetailScreenActivity
import com.emud.pilemapps.R
import com.emud.pilemapps.model.Checkout
import com.emud.pilemapps.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)


        var statusA4:Boolean = false
        var statusB1:Boolean = false
        var total:Int = 0

        val datalist = ArrayList<Checkout>()

            val data =intent.getParcelableExtra<Film>("data")
            tv_kursi.text = data!!.judul

            a4.setOnClickListener {
                if (statusA4) {
                    a4.setImageResource(R.drawable.shape_line_brown)
                    statusA4 =false
                    total -= 1
                    beliTiket(total)
                } else {
                    a4.setImageResource(R.drawable.ic_selected)
                    statusA4 =true
                    total += 1
                    beliTiket(total)

                    val data = Checkout("A4", "70000")
                    datalist.add(data)
                }

            }

            b1.setOnClickListener {
                if (statusB1) {
                    b1.setImageResource(R.drawable.shape_line_brown)
                    statusB1 =false
                    total -= 1
                    beliTiket(total)
                } else {
                    b1.setImageResource(R.drawable.ic_selected)
                    statusB1 =true
                    total += 1
                    beliTiket(total)

                    val data = Checkout("B1", "80000")
                    datalist.add(data)
                }
            }
        btn_home.setOnClickListener {
            var intent = Intent(this, CheckOutActivity::class.java).putExtra("data", datalist)
            startActivity(intent)
        }
        }

        private fun beliTiket(total: Int) {
            if (total == 0){
                btn_home.setText ("Beli Tiket")
                btn_home.visibility = View.INVISIBLE
            } else {
                btn_home.setText ("Beli Tiket ($total)")
                btn_home.visibility = View.VISIBLE
            }
        }
    }