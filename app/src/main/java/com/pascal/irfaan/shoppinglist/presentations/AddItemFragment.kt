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
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentAddItemBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import com.pascal.irfaan.shoppinglist.viewmodel.ItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddItemFragment() : Fragment() {

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: ItemViewModel
    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initModel()
        subscribe()
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
                viewModel.inputValidation(
                    inputItemName.text.toString(),
                    inputShoppingDate.text.toString(),
                    inputQuantity.text.toString(),
                    inputNotes.text.toString()
                )
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

    private fun initModel() {
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.inputValidation.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "INI LAGI LOADING", Toast.LENGTH_LONG).show()

                }
                ResourceStatus.SUCCESS -> {
                    val item = Item(
                        binding.inputShoppingDate.text.toString(),
                        binding.inputItemName.text.toString(),
                        binding.inputQuantity.text.toString(),
                        binding.inputNotes.text.toString()
                    )
                    viewModel.itemList.add(item)
                    Toast.makeText(requireContext(), "ADD ${item.itemName} SUCCESSFULLY", Toast.LENGTH_LONG).show()
                    clearEditText()
                }
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = AddItemFragment()
    }
}