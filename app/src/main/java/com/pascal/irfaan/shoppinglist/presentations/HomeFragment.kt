package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewListShopping = ViewListShopping()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.apply {
            goToAddItem.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_addItem)
            }
            goToList.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_viewListShopping)
            }
            goToAccount.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_accountFragment)
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
