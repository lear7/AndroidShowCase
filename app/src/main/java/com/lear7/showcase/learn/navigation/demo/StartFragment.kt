package com.lear7.showcase.learn.navigation.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.lear7.showcase.R
import kotlinx.android.synthetic.main.fragment_nav_start.*

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
        return inflater.inflate(R.layout.fragment_nav_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = view.findNavController()
        btn_login_in.setOnClickListener {
            val action = StartFragmentDirections.actionStartFragmentToLoginFragment()
            navController.navigate(action)
        }
    }
}