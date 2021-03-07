package com.pascal.irfaan.shoppinglist.presentations.item.add

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
import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.data.models.ItemsEntity
import com.pascal.irfaan.shoppinglist.data.models.ItemsRequest
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.repositories.impl.ItemRepositoriesImpl
import com.pascal.irfaan.shoppinglist.databinding.FragmentAddItemBinding
import com.pascal.irfaan.shoppinglist.presentations.components.LoadingDialog
import com.pascal.irfaan.shoppinglist.presentations.item.list.ListItemViewModel
import com.pascal.irfaan.shoppinglist.utils.DateDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class AddEditItemFragment() : Fragment() {

    private var itemUpdate: ItemsEntity? = null
    private lateinit var binding: FragmentAddItemBinding
    private lateinit var navController: NavController

    private lateinit var listViewModel: ListItemViewModel
    private lateinit var addItemViewModel: AddItemViewModel

    private lateinit var loadingDialog: AlertDialog
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
        if (itemUpdate != null) {
            binding.apply {
                itemUpdate?.apply {
                    val calendar = Calendar.getInstance()
                    val dateShopSubString = dateShop.substring(0,10)
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    calendar.time = sdf.parse(dateShopSubString)
                    val stringDate = formatDate.format(calendar.time)
                    inputShoppingDate.setText(stringDate)
                    inputQuantity.setText(quantity.toString())
                    inputItemName.setText(itemName)
                    inputNotes.setText(notes)
                    inputShoppingDate.isEnabled = false
                    calendarButton.setOnClickListener {
                        DateDialog.show(requireContext()) { shoppingDate ->
                            inputShoppingDate.setText(shoppingDate)
                        }
                    }
                    addEditShoppingItemButton.setOnClickListener {
                        val itemId = itemUpdate?.id!!
                        val updatedItem = ItemsRequest(
                            dateShop = inputShoppingDate.text.toString(),
                            itemName = inputItemName.text.toString(),
                            quantity = inputQuantity.text.toString().toInt(),
                            notes = inputNotes.text.toString()
                        )
                        addItemViewModel.updateItemById(itemId, updatedItem)
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
                    DateDialog.show(requireContext()) { taskDate ->
                        inputShoppingDate.setText(taskDate)
                    }
                }
                addEditShoppingItemButton.setOnClickListener {
                    val inputItemNameString = inputItemName.text.toString()
                    val inputshoppingDateString = inputShoppingDate.text.toString()
                    val inputQuantityString = inputQuantity.text.toString()
                    val inputNotesString = inputNotes.text.toString()
                    addItemViewModel.inputValidation(
                        inputItemNameString, inputshoppingDateString, inputQuantityString, inputNotesString
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
        listViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoriesImpl()
                return ListItemViewModel(repo) as T
            }

        }).get(ListItemViewModel::class.java)
        addItemViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoriesImpl()
                return AddItemViewModel(repo) as T
            }

        }).get(AddItemViewModel::class.java)
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
                    binding.apply {
                        val inputItemNameString = inputItemName.text.toString()
                        val inputshoppingDateString = inputShoppingDate.text.toString()
                        val inputQuantityString = inputQuantity.text.toString()
                        val inputNotesString = inputNotes.text.toString()
                        val addItemRequest = ItemsRequest(
                            itemName = inputItemNameString,
                            dateShop = inputshoppingDateString,
                            quantity = inputQuantityString.toInt(),
                            notes = inputNotesString)
                        addEditShoppingItemButton.isEnabled = true
                        addItemViewModel.addItemShopping(addItemRequest)
                    }
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.addEditShoppingItemButton.isEnabled = true
                }
            }
        })
        addItemViewModel.addItemShoppingLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.SUCCESS -> {
                    val response = it.data as ItemsResponse
                    val itemName = response.data.itemName
                    clearEditText()
                    Toast.makeText(requireContext(), "Add item with $itemName", Toast.LENGTH_LONG).show()
                }
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "Waiting for Response", Toast.LENGTH_SHORT).show()
                }
            }
        })
        addItemViewModel.updateItemShoppingLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.SUCCESS -> {
                    val response = it.data as ItemsResponse
                    val itemName = response.data.itemName
                    Toast.makeText(requireContext(), "Update item with $itemName", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigate(R.id.action_addItem_to_viewListShopping)
                }
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "Waiting for Response", Toast.LENGTH_SHORT).show()
                }
            }
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