package com.lear7.showcase.component.navigation.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lear7.showcase.R

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_nav_explore, container, false)
    }
}