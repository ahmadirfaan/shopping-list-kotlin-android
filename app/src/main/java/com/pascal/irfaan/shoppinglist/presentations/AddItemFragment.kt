package com.pascal.irfaan.shoppinglist.presentations

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentAddItemBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddItemFragment() : Fragment() {

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var navController: NavController
    private lateinit var item: Item
    private lateinit var viewModel: ItemViewModel
    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddItemBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.apply {
            goToList.setOnClickListener { this }
            inputShoppingDate.isEnabled = false
            val getDate = Calendar.getInstance()
            inputShoppingDate.setText(formatDate.format(getDate.time))
            calendarButton.setOnClickListener {
                val getDate = Calendar.getInstance()
                val datepicker = DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val selectDate = Calendar.getInstance()
                        selectDate.set(Calendar.YEAR, year)
                        selectDate.set(Calendar.MONTH, month)
                        selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val date = formatDate.format(selectDate.time)
                        Toast.makeText(requireContext(), "Date : $date", Toast.LENGTH_SHORT).show()
                        inputShoppingDate.setText(date)
                    },
                    getDate.get(Calendar.YEAR),
                    getDate.get(Calendar.MONTH),
                    getDate.get(Calendar.DAY_OF_MONTH)
                )
                datepicker.show()
            }
        }
        binding.apply {
            addShoppingItemButton.setOnClickListener {
                val inputShoppingDate = inputShoppingDate.text.toString()
                val inputItemName = inputItemName.text.toString()
                val inputQuantity = inputQuantity.text.toString()
                val inputNotes = inputNotes.text.toString()
                when {
                    inputShoppingDate.isNullOrBlank() -> {
                        Toast.makeText(requireContext(), "THE FIELD SHOPPING DATE MUST NOT EMPTY", Toast.LENGTH_LONG).show()
                    }
                    inputItemName.isNullOrBlank() -> {
                        Toast.makeText(requireContext(), "THE FIELD INPUT ITEM NAME EMPTY", Toast.LENGTH_LONG).show()
                    }
                    inputQuantity.isNullOrBlank() -> {
                        Toast.makeText(requireContext(), "THE FIELD INPUT QUANTITY EMPTY", Toast.LENGTH_LONG).show()
                    }
                    inputNotes.isNullOrBlank() -> {
                        Toast.makeText(requireContext(), "THE FIELD INPUT NOTES EMPTY", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        item = Item(inputShoppingDate, inputItemName, inputQuantity, inputNotes)
                        viewModel.itemList.add(item)
                        Toast.makeText(requireContext(), "ADD ${item.itemName} SUCCESSFULLY", Toast.LENGTH_LONG).show()
                        clearEditText()
                    }
                }

            }
        }

        binding.apply {
            goToList.setOnClickListener {
                navController.navigate(R.id.action_addItem_to_viewListShopping)
            }
        }


    }


    private fun clearEditText() {
        binding.apply {
            inputShoppingDate.text?.clear()
            inputItemName.text?.clear()
            inputQuantity.text?.clear()
            inputNotes.text?.clear()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = AddItemFragment()
    }
}