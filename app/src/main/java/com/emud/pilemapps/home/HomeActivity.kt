package com.emud.pilemapps.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.emud.pilemapps.R
import com.emud.pilemapps.home.dashboard.DashboardFragment
import com.emud.pilemapps.home.setting.settingFragment
import com.emud.pilemapps.home.tiket.TiketFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentTiket = TiketFragment()
        val fragmentSetting = settingFragment()
        val fragmentHome = DashboardFragment()

        setFragment(fragmentHome)

        iv_menu1.setOnClickListener{
            setFragment(fragmentHome)

        }
        iv_menu2.setOnClickListener {
            setFragment(fragmentTiket)
        }
        iv_menu3.setOnClickListener {
            setFragment(fragmentSetting)
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentmanager = supportFragmentManager
        val fragmentTransacion = fragmentmanager.beginTransaction()
        fragmentTransacion.replace(R.id.layout_frame, fragment)
        fragmentTransacion.commit()
    }
}

