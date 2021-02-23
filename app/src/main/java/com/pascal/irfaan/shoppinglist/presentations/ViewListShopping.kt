package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentViewListShoppingBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemListConfig
import com.pascal.irfaan.shoppinglist.utils.ItemViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ViewListShopping() : Fragment()  {

    private lateinit var binding : FragmentViewListShoppingBinding
    private lateinit var navController : NavController
    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
        binding = FragmentViewListShoppingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.apply {
            viewListItem.text = viewListToString()
            backButtonToCreateItem.setOnClickListener {
                view?.findNavController()?.popBackStack()

            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("INI FRAGMENT VIEW LIST", "INI ON PAUSE" )
    }

    override fun onResume() {
        super.onResume()
        Log.i("INI FRAGMENT VIEW LIST", "INI ON resume" )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("INI FRAGMENT VIEW LIST", "INI ON DESTROY ${ItemListConfig.toString()}" )
    }

    private fun viewListToString(): String {
        var stringBuilder = StringBuilder()
        for ((index, item) in viewModel.itemList.withIndex()) {
            stringBuilder.append(
                "${index + 1}. Date Transaction : ${item.shoppingDate}, " +
                        "Item Name : ${item.itemName}, " +
                        "Quantity : ${item.quantity}, " +
                        "Notes : ${item.notes} "
            )
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }


    companion object {
        fun newInstance() = ViewListShopping()
    }
}