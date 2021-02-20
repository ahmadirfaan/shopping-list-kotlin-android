package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.OnNavigationListener

class AddItem(val onNavigationListener: OnNavigationListener) : Fragment() {

    private lateinit var addShoppingItemButton: Button
    private lateinit var item: Item
    private lateinit var inputShoppingDate: TextInputEditText
    private lateinit var inputItemName: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var inputNotes: TextInputEditText

    private val itemList = mutableListOf<Item>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)
        inputShoppingDate = view.findViewById(R.id.inputShoppingDate)
        inputItemName = view.findViewById(R.id.inputItemName)
        inputQuantity = view.findViewById(R.id.inputQuantity)
        inputNotes = view.findViewById(R.id.inputNotes)
        addShoppingItemButton = view.findViewById(R.id.addShoppingItemButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addShoppingItemButton.setOnClickListener {
            item = convertInputToItem(inputShoppingDate, inputItemName, inputQuantity, inputNotes)
//            Log.i("SHOPPING DATE", inputShoppingDate.text.toString())
//            Log.i("INI INTERFACE FRAGMENT ADD ITEM", item.toString())
            itemList.add(item)
            clearEditText()
        }
        onNavigationListener.addShop(itemList)

    }

    override fun onResume() {
        super.onResume()
        for (i in itemList) {
            Log.i("INI MUTABLES LIST DI ADD ITEM", "$i")
        }
    }

    private fun convertInputToItem(
        inputShoppingDate: TextInputEditText?,
        inputItemName: TextInputEditText?,
        inputQuantity: TextInputEditText?,
        inputNotes: TextInputEditText?
    ): Item {
//        Log.i("INI CONVERT ITEM FUNGSI", "MASUKK DONG PLISSS")
        val shoppingDate = inputShoppingDate?.text.toString()
        val itemName = inputItemName?.text.toString()
        val quantity = inputQuantity?.text.toString()
        val notes = inputNotes?.text.toString()
        item = Item(shoppingDate, itemName, quantity, notes)
//        Log.i("INI CONVERT ITEM FUNGSI CETAK ITEM", item.toString())
        return item
    }

    private fun clearEditText() {
        inputShoppingDate.text?.clear()
        inputItemName.text?.clear()
        inputQuantity.text?.clear()
        inputNotes.text?.clear()

    }

    companion object {
        @JvmStatic
        fun newInstance(onNavigationListener: OnNavigationListener) = AddItem(onNavigationListener)
    }
}