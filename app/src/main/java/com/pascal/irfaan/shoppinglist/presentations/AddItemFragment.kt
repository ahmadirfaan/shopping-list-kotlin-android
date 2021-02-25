package com.pascal.irfaan.shoppinglist.presentations

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentAddItemBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.LoadingDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import com.pascal.irfaan.shoppinglist.viewmodel.ItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddItemFragment() : Fragment() {

    private lateinit var binding: FragmentAddItemBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: ItemViewModel
    private lateinit var loadingDialog : AlertDialog
    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initModel()
        subscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddItemBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)
        loadingDialog = LoadingDialog.build(requireContext())
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
                    loadingDialog.show()
                    binding.addShoppingItemButton.isEnabled = false
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.addShoppingItemButton.isEnabled = true
                    val item = Item(
                        binding.inputShoppingDate.text.toString(),
                        binding.inputItemName.text.toString(),
                        binding.inputQuantity.text.toString(),
                        binding.inputNotes.text.toString()
                    )
                    viewModel.addItemToList(item)
                    Toast.makeText(requireContext(), "ADD ${item.itemName} SUCCESSFULLY", Toast.LENGTH_LONG).show()
                    clearEditText()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.addShoppingItemButton.isEnabled = true
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        Log.i("INI ADD ITEM FRAGMENT", "ON PAUSE")

    }

    override fun onResume() {
        super.onResume()
        Log.i("INI ADD ITEM FRAGMENT", "ON RESUME")

    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("INI ADD ITEM FRAGMENT", "ON DESTROY")

    }


    companion object {
        @JvmStatic
        fun newInstance() = AddItemFragment()
    }
}