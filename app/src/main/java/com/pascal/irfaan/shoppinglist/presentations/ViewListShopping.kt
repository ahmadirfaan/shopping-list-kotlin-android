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
import androidx.navigation.findNavController
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemListConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ViewListShopping() : Fragment(), View.OnClickListener  {

    private lateinit var backButton : Button
    private lateinit var navController : NavController
    private lateinit var viewListItem : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
        backButton = view.findViewById(R.id.backButtonToCreateItem)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewListItem = (getView()?.findViewById<TextView>(R.id.viewListItem) ?: null) as TextView
        viewListItem.text = viewListToString()
        backButton.setOnClickListener(this)
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
        for ((index, item) in ItemListConfig.withIndex()) {
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

    override fun onClick(v: View?) {
        when(v) {
            backButton -> view?.findNavController()?.popBackStack()
        }
    }

    companion object {
        fun newInstance() = ViewListShopping()
    }
}