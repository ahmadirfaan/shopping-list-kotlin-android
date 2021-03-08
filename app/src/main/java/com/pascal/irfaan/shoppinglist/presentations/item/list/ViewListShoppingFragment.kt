package com.pascal.irfaan.shoppinglist.presentations.item.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.data.dao.ItemDatabase
import com.pascal.irfaan.shoppinglist.data.models.ItemsEntity
import com.pascal.irfaan.shoppinglist.data.models.ItemsResponse
import com.pascal.irfaan.shoppinglist.data.models.ResponsePagination
import com.pascal.irfaan.shoppinglist.databinding.FragmentViewListShoppingBinding
import com.pascal.irfaan.shoppinglist.data.repositories.impl.ItemRepositoriesImpl
import com.pascal.irfaan.shoppinglist.presentations.components.LoadingDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewListShoppingFragment() : Fragment() {

    private lateinit var itemListViewAdapter: ItemListViewAdapter
    private lateinit var binding: FragmentViewListShoppingBinding
    private lateinit var viewModel: ListItemViewModel
    private lateinit var loadingDialog: AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewmodel()
        itemListViewAdapter = ItemListViewAdapter(viewModel)
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog.build(requireContext())
        binding = FragmentViewListShoppingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backButtonToCreateItem.setOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            addFabItem.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_viewListShopping_to_addItem)
            }
            binding.itemListRecyclerView.apply {
                adapter = itemListViewAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewModel.getAllItemListData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("INI VIEW LIST FRAGMENT", "ON DESTROY")
    }

    override fun onPause() {
        super.onPause()
        Log.i("INI VIEW LIST FRAGMENT", "ON PAUSE")

    }

    override fun onResume() {
        super.onResume()
        Log.i("INI VIEW LIST FRAGMENT", "ON RESUME")

    }



    private fun initViewmodel() {
        viewModel = ViewModelProvider(this).get(ListItemViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.itemListLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val response = it.data as ResponsePagination
                    val listItem = response.data.list
                    itemListViewAdapter.setItemList(listItem)
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.itemDeleteLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    val response = it.data as ItemsResponse
                    val deleted = response.data
                    Toast.makeText(requireContext(), "DELETED ITEM with id : ${deleted.id} and name : ${deleted.itemName}", Toast.LENGTH_SHORT).show()
                    viewModel.getAllItemListData()
                    loadingDialog.hide()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.itemFindByIdLiveData.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    val response = it.data as ItemsResponse
                    val findItemById = response.data
                    val bundle = bundleOf("item_update" to findItemById)
                    Navigation.findNavController(requireView()).navigate(R.id.action_viewListShopping_to_addItem, bundle)
                    loadingDialog.hide()

                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    companion object {
        fun newInstance() = ViewListShoppingFragment()
    }
}