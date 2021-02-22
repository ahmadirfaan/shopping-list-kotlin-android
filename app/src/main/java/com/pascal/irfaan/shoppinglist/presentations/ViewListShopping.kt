package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item


class ViewListShopping() : Fragment(), View.OnClickListener  {

    private lateinit var backButton : Button
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
        backButton = view.findViewById(R.id.backButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        backButton.setOnClickListener(this)

    }

//    private fun viewListToString(): String {
//        var stringBuilder = StringBuilder()
//        for ((index, item) in itemList.withIndex()) {
//            stringBuilder.append(
//                "${index + 1}. Date Transaction : ${item.shoppingDate}, " +
//                        "Item Name : ${item.itemName}, " +
//                        "Quantity : ${item.quantity}, " +
//                        "Notes : ${item.notes} "
//            )
//            stringBuilder.append("\n")
//        }
//        return stringBuilder.toString()
//    }

    override fun onClick(v: View?) {
        when(v) {
            backButton -> navController.navigate(R.id.action_viewListShopping_to_addItem)
        }
    }

    companion object {
        fun newInstance() = ViewListShopping()
    }
}