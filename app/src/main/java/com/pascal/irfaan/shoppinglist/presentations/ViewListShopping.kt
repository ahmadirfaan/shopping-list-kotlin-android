package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item


class ViewListShopping(val itemList: MutableList<Item>) : Fragment() {

    private lateinit var viewListItem: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewListItem = (getView()?.findViewById<TextView>(R.id.viewListItem) ?: null) as TextView
        viewListItem.text = viewListToString()
        Log.i("INI ITEM DI VIEW LIST", itemList.toString())
    }

    private fun viewListToString(): String {
        var stringBuilder = StringBuilder()
        for ((index, item) in itemList.withIndex()) {
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
        fun newInstance(itemList: MutableList<Item>) = ViewListShopping(itemList)
    }
}