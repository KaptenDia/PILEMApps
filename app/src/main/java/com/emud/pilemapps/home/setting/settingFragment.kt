package com.emud.pilemapps.home.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.emud.pilemapps.R
import kotlinx.android.synthetic.main.fragment_setting.*

class settingFragment : Fragment() {

    private lateinit var preferences: com.emud.pilemapps.utility.Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = com.emud.pilemapps.utility.Preferences(requireContext())

        tv_nama1.text = preferences.getValue("nama")
        tv_email.text = preferences.getValue("email")

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile1)
    }
}