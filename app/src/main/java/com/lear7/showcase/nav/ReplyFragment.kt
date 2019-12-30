package com.lear7.showcase.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R
import kotlinx.android.synthetic.main.fragment_reply.*

/**
@author Lear
@description
@date 2019/12/30 14:42
 */
class ReplyFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reply, container, false)

        view.findViewById<Button>(R.id.btn_go_to_play).setOnClickListener({
            val action = ReplyFragmentDirections.actionReplyFragmentToPlayingFragment()
            view.findNavController().navigate(action)
//            Navigation.findNavController(view).navigate(R.id.action_hello_fragment_to_reply_fragment)
        })

        val greeting = arguments?.getString("greeting_content") ?: "Default Content"
        tv_greeting.text = greeting

        return view
    }
}