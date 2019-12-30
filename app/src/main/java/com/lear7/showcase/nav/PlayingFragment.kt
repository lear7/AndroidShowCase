package com.lear7.showcase.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R
import kotlinx.android.synthetic.main.fragment_playing.*
import kotlinx.android.synthetic.main.fragment_reply.*

/**
@author Lear
@description
@date 2019/12/30 14:39
 */
class PlayingFragment:  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playing, container, false)

        btn_go_home.setOnClickListener({
            val action = PlayingFragmentDirections.actionPlayingFragmentToHomeFragment();
            view.findNavController().navigate(action)
        })

        return view
    }
}