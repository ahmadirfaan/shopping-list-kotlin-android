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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentViewListShoppingBinding
import com.pascal.irfaan.shoppinglist.repositories.impl.ItemRepositoryImpl
import com.pascal.irfaan.shoppinglist.presentations.components.LoadingDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus


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
        viewModel.loadItemData()
        viewModel.validationItemList()
        binding.apply {
            backButtonToCreateItem.setOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            addFabItem.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_viewListShopping_to_addItem)
            }
        }

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
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return ListItemViewModel(repo) as T
            }

        }).get(ListItemViewModel::class.java)
        Log.i("INI VIEW LIST CHECK ITEMLIST", "${ItemRepositoryImpl.itemList}")
        Log.i("INI VIEW LIST CHECK ITEMLIST LIVE DATA", "${viewModel.itemListLiveData.value.toString()}")
    }

    private fun subscribe() {
        viewModel.validationItemList.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.apply{
                        itemListRecyclerView.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = itemListViewAdapter
                        }
                    }
                    Toast.makeText(requireContext(), "LIST ITEM DENGAN JUMLAH ${viewModel.getItemList().size}", Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                }
            }
        })
        viewModel.itemListLiveData.observe(this, {
            itemListViewAdapter.setItemList(it)
        })
        viewModel.itemUpdateLiveData.observe(this, {
            Navigation.findNavController(requireView()).navigate(R.id.action_viewListShopping_to_addItem, bundleOf("item_update" to it))
        })
    }


    companion object {
        fun newInstance() = ViewListShoppingFragment()
    }
}