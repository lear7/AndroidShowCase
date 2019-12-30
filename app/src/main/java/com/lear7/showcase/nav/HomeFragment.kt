package com.lear7.showcase.nav

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R
import org.jetbrains.anko.support.v4.toast

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

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener({
            val action = HomeFragmentDirections.actionHomeFragmentToStartFragment()
            view.findNavController().navigate(action)
        })

        return view
    }
}