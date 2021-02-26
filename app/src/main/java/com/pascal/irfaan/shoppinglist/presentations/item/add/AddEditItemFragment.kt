package com.pascal.irfaan.shoppinglist.presentations.item.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentAddItemBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.repositories.impl.ItemRepositoryImpl
import com.pascal.irfaan.shoppinglist.presentations.components.LoadingDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import com.pascal.irfaan.shoppinglist.presentations.item.list.ListItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditItemFragment() : Fragment() {

    private var itemUpdate : Item? = null
    private lateinit var binding: FragmentAddItemBinding
    private lateinit var navController: NavController

    private lateinit var listViewModel: ListItemViewModel
    private lateinit var addItemViewModel: AddItemViewModel
    private lateinit var updateItemViewModel : UpdateItemViewModel

    private lateinit var loadingDialog : AlertDialog
    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            itemUpdate = this.getParcelable("item_update")
        }
        initViewModel()
        subscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddItemBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog.build(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if(itemUpdate != null) {
            binding.apply {
                itemUpdate?.apply {
                    inputShoppingDate.setText(shoppingDate)
                    inputQuantity.setText(quantity)
                    inputItemName.setText(itemName)
                    inputNotes.setText(quantity)
                    inputShoppingDate.isEnabled = false
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
                    addEditShoppingItemButton.setOnClickListener {
                        val updateItem = copy(
                            shoppingDate = inputShoppingDate.text.toString(),
                            itemName = inputItemName.text.toString(),
                            quantity = inputQuantity.text.toString(),
                            notes = inputNotes.text.toString()
                        )
                        updateItemViewModel.OnUpdate(updateItem)
                    }
                }
                titleShopping.setText("EDIT ITEM")
                addEditShoppingItemButton.text = "UPDATE ITEM"
            }
        } else {
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
                addEditShoppingItemButton.setOnClickListener {
                    addItemViewModel.inputValidation(
                        inputItemName.text.toString(),
                        inputShoppingDate.text.toString(),
                        inputQuantity.text.toString(),
                        inputNotes.text.toString()
                    )
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

    private fun initViewModel() {
        listViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return ListItemViewModel(repo) as T
            }

        }).get(ListItemViewModel::class.java)
        updateItemViewModel  = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return UpdateItemViewModel(repo) as T
            }

        }).get(UpdateItemViewModel::class.java)
        addItemViewModel = ViewModelProvider(this).get(AddItemViewModel::class.java)
    }

    private fun subscribe() {
        addItemViewModel.inputValidation.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                    binding.addEditShoppingItemButton.isEnabled = false
                }
                ResourceStatus.SUCCESS -> {
                    Log.i("ini add item fragment", "RESOURCE STATE SUCCESS")
                    loadingDialog.hide()
                    binding.addEditShoppingItemButton.isEnabled = true
                    val item = Item(
                        "",
                        binding.inputShoppingDate.text.toString(),
                        binding.inputItemName.text.toString(),
                        binding.inputQuantity.text.toString(),
                        binding.inputNotes.text.toString()
                    )
                    listViewModel.addItemToList(item)
                    Toast.makeText(requireContext(), "ADD ${item.itemName} SUCCESSFULLY", Toast.LENGTH_LONG).show()
                    clearEditText()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    binding.addEditShoppingItemButton.isEnabled = true
                }
            }
        })
        updateItemViewModel.updateStatus.observe(this, {
            Navigation.findNavController(requireView()).navigate(R.id.action_addItem_to_viewListShopping)
        })
    }

    override fun onPause() {
        super.onPause()
        Log.i("INI ADD ITEM FRAGMENT", "ON PAUSE")
        binding.apply {
            addItemViewModel.inputValidation(
                inputItemName.text.toString(),
                inputShoppingDate.text.toString(),
                inputQuantity.text.toString(),
                inputNotes.text.toString()
            )
        }

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
        fun newInstance() = AddEditItemFragment()
    }
}