package com.lear7.showcase.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R

/**
@author Lear
@description
@date 2019/12/30 14:39
 */
class HomeFragment:  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nav_home, container, false)

        view.findViewById<Button>(R.id.btn_say_hello).setOnClickListener({
            val action = HomeFragmentDirections.actionHomeFragmentToReplyFragment("Hello, green bird!");
            view.findNavController().navigate(action)
//            Navigation.findNavController(view).navigate(R.id.action_hello_fragment_to_reply_fragment)
        })

        return view
    }
}