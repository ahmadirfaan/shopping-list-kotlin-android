package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.OnNavigationListener

class AddItem() : Fragment(), View.OnClickListener {

    private lateinit var goToList : Button
    private lateinit var navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)
        goToList = view.findViewById(R.id.goToList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        goToList.setOnClickListener(this)

    }

//    private fun convertInputToItem(
//        inputShoppingDate: TextInputEditText?,
//        inputItemName: TextInputEditText?,
//        inputQuantity: TextInputEditText?,
//        inputNotes: TextInputEditText?
//    ): Item {
////        Log.i("INI CONVERT ITEM FUNGSI", "MASUKK DONG PLISSS")
//        val shoppingDate = inputShoppingDate?.text.toString()
//        val itemName = inputItemName?.text.toString()
//        val quantity = inputQuantity?.text.toString()
//        val notes = inputNotes?.text.toString()
//        item = Item(shoppingDate, itemName, quantity, notes)
////        Log.i("INI CONVERT ITEM FUNGSI CETAK ITEM", item.toString())
//        return item
//    }

//    private fun clearEditText() {
//        inputShoppingDate.text?.clear()
//        inputItemName.text?.clear()
//        inputQuantity.text?.clear()
//        inputNotes.text?.clear()
//
//    }

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