package com.lear7.showcase.nav.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R
import com.lear7.showcase.nav.demo.StartFragmentDirections
import kotlinx.android.synthetic.main.fragment_bottom_nav_more.*

/**
 * A simple [Fragment] subclass.
 */
class MoreFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_nav_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            val action = MoreFragmentDirections.actionMoreToHome()
            view.findNavController().navigate(action)
        }
    }
}