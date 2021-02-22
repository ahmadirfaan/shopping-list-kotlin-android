package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.irfaan.shoppinglist.R


class HomeFragment : Fragment() {

    private lateinit var goToAddItemButton : Button
    private lateinit var goToListButton : Button
    private lateinit var goToAccountButton : Button
    private lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        goToAddItemButton = view.findViewById(R.id.goToAddItem)
        goToListButton = view.findViewById(R.id.goToList)
        goToAccountButton = view.findViewById(R.id.goToAccount)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        goToAddItemButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addItem)
        }
        goToListButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_viewListShopping)
        }
        goToAccountButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_accountFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
