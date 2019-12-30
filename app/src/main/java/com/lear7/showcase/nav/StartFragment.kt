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
class StartFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nav_start, container, false)

        view.findViewById<Button>(R.id.btn_login_in).setOnClickListener({
            val action = StartFragmentDirections.actionStartFragmentToLoginFragment()
            view.findNavController().navigate(action)
        })

        return view
    }
}