package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.OnNavigationListener

class AddItem() : Fragment(), View.OnClickListener {

    private lateinit var addShoppingItemButton: Button
    private lateinit var item: Item
    private lateinit var inputShoppingDate: TextInputEditText
    private lateinit var inputItemName: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var inputNotes: TextInputEditText
    private lateinit var goToList : Button
    private lateinit var navController : NavController
    private val itemList = mutableListOf<Item>()


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
        goToList = view.findViewById(R.id.goToList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        goToList.setOnClickListener(this)
        addShoppingItemButton.setOnClickListener {
            item = convertInputToItem(inputShoppingDate, inputItemName, inputQuantity, inputNotes)
//            Log.i("SHOPPING DATE", inputShoppingDate.text.toString())
//            Log.i("INI INTERFACE FRAGMENT ADD ITEM", item.toString())
            itemList.add(item)
            Toast.makeText(requireContext(), "Add ${item.itemName} Successfully", Toast.LENGTH_LONG).show()
            clearEditText()
            view.findNavController().navigate(R.id.action_addItem_to_viewListShopping, bundleOf("ITEM_LIST" to itemList))
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
        fun newInstance() = AddItem()
    }

    override fun onClick(v: View?) {

        when(v) {
            goToList -> navController.navigate(R.id.action_addItem_to_viewListShopping)
        }
    }
}