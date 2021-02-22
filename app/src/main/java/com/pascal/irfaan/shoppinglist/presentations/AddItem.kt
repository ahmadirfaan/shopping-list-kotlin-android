package com.pascal.irfaan.shoppinglist.presentations

import android.app.DatePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemListConfig
import java.text.SimpleDateFormat
import java.util.*

class AddItem() : Fragment(), View.OnClickListener {

    private lateinit var addShoppingItemButton: Button
    private lateinit var item: Item
    private lateinit var inputShoppingDate: TextInputEditText
    private lateinit var inputItemName: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var inputNotes: TextInputEditText
    private lateinit var goToList : Button
    private lateinit var calendarButton: ImageButton
    private lateinit var navController : NavController

    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)


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
        calendarButton = view.findViewById(R.id.calendarButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        goToList.setOnClickListener(this)
        inputShoppingDate.isEnabled = false
        val getDate = Calendar.getInstance()

        inputShoppingDate.setText(formatDate.format(getDate.time))
        calendarButton.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datepicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener {
                    view, year, month, dayOfMonth ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = formatDate.format(selectDate.time)
                Toast.makeText(requireContext(), "Date : $date", Toast.LENGTH_SHORT).show()
                inputShoppingDate.setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()
        }
        addShoppingItemButton.setOnClickListener {
//            Log.i("SHOPPING DATE", inputShoppingDate.text.toString())
//            Log.i("INI INTERFACE FRAGMENT ADD ITEM", item.toString())
            item = convertInputToItem(inputShoppingDate, inputItemName, inputQuantity, inputNotes)
            ItemListConfig.add(item)
            Toast.makeText(requireContext(), "Add ${item.itemName} Successfully", Toast.LENGTH_LONG).show()
            clearEditText()
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
        val bundle = bundleOf("ITEM_LIST" to ItemListConfig)
        when(v) {
            goToList -> {
                navController.navigate(R.id.action_addItem_to_viewListShopping, bundle)
            }
        }
    }
}