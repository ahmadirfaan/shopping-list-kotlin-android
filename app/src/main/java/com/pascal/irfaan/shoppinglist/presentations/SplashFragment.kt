package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.utils.OnNavigationListener


class SplashFragment(private val onNavigationListener: OnNavigationListener) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        @JvmStatic
        fun newInstance(onNavigationListener: OnNavigationListener) = SplashFragment(onNavigationListener)
    }
}