package com.lear7.showcase.component.navigation.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R
import kotlinx.android.synthetic.main.fragment_nav_home.*

/**
@author Lear
@description
@date 2019/12/30 14:42
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nav_home, container, false)
        btn_logout.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToStartFragment()
            view.findNavController().navigate(action)
        }

        return view
    }
}