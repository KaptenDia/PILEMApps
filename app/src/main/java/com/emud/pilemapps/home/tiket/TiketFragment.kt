package com.emud.pilemapps.home.tiket

import android.content.Intent
import android.os.Bundle
import com.emud.pilemapps.utility.Preferences
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.emud.pilemapps.R
import com.emud.pilemapps.home.dashboard.ComingSoonAdapter
import com.emud.pilemapps.model.Film
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_tiket.*

class TiketFragment : Fragment() {

    private lateinit var preference : Preferences
    private lateinit var mDatabase : DatabaseReference
    private var datalist = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = Preferences(requireContext())
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        rc_tiket.layoutManager = LinearLayoutManager(requireContext())
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                for (dataSnapshot in snapshot.children) {
                    val film = dataSnapshot.getValue(Film::class.java)
                    datalist.add(film!!)
                }

                rc_tiket.adapter = ComingSoonAdapter(datalist){
                    val intent= Intent(context, TiketActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                tv_tiket.setText("${datalist.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}
