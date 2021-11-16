package com.emud.pilemapps.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emud.pilemapps.DetailScreenActivity
import com.emud.pilemapps.R
import com.emud.pilemapps.model.Film
import com.emud.pilemapps.utility.Preferences
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    private lateinit var preferences : Preferences
    private lateinit var mDatabase : DatabaseReference

    private var datalist = ArrayList<Film>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.text = preferences.getValue("nama")
        if (preferences.getValue("saldo").equals("")){
            currency(preferences.getValue("saldo")!!.toDouble(),tv_saldo)
        }

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        getData()

    }

    private fun getData() {
       mDatabase.addValueEventListener(object : ValueEventListener{
           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context, ""+error.message,Toast.LENGTH_LONG).show()
           }

           override fun onDataChange(snapshot: DataSnapshot) {

               datalist.clear()
               for (dataSnapshot in snapshot.children){
                   val film = dataSnapshot.getValue(Film::class.java)
                   datalist.add(film!!)
               }

               rv_now_playing.adapter = NowPlayingAdapter(datalist){
                   val intent = Intent(context, DetailScreenActivity::class.java).putExtra("data",it)
                   startActivity(intent)

               }
               rv_coming_soon.adapter = ComingSoonAdapter(datalist){
                   val intent = Intent(context, DetailScreenActivity::class.java).putExtra("data",it)
                   startActivity(intent)
               }

           }
       })
    }

    private fun currency(harga : Double, textview : TextView){
        val localID = Locale("in","ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.text = format.format(harga)
    }

}