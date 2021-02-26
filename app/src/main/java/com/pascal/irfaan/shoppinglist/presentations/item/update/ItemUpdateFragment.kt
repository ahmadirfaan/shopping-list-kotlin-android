package com.pascal.irfaan.shoppinglist.presentations.item.update

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentItemUpdateBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.presentations.item.list.ListItemViewModel
import com.pascal.irfaan.shoppinglist.repositories.impl.ItemRepositoryImpl


class ItemUpdateFragment : Fragment() {
    private var itemUpdate : Item? = null
    private lateinit var binding : FragmentItemUpdateBinding
    private lateinit var viewModel : UpdateItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemUpdate = it.getParcelable("item_update")
        }
        initViewModel()
        subscribe()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemUpdateBinding.inflate(inflater)
        binding.apply {
            itemUpdate?.apply {
                inputEditShoppingDate.setText(shoppingDate)
                inputEditItemName.setText(itemName)
                inputEditQuantity.setText(quantity)
                inputEditNotes.setText(notes)
                editShoppingItemButton.setOnClickListener {
                    val updateItem = copy(
                        id = this.id,
                        shoppingDate = inputEditShoppingDate.text.toString(),
                        itemName = inputEditItemName.text.toString(),
                        quantity = inputEditQuantity.text.toString(),
                        notes = inputEditNotes.text.toString()
                    )
                    viewModel.OnUpdate(updateItem)
                }
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return UpdateItemViewModel(repo) as T
            }

        }).get(UpdateItemViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.updateStatus.observe(this, {
            Navigation.findNavController(requireView()).navigate(R.id.action_itemUpdateFragment_to_viewListShopping)
        })
    }

    companion object {
        fun newInstance() = ItemUpdateFragment()
    }


}