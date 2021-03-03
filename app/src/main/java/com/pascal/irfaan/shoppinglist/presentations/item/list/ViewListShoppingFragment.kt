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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentViewListShoppingBinding
import com.pascal.irfaan.shoppinglist.presentations.components.LoadingDialog
import com.pascal.irfaan.shoppinglist.repositories.impl.ItemRepositoryImpl
import com.pascal.irfaan.shoppinglist.utils.Pagination
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus


class ViewListShoppingFragment() : Fragment() {

    private lateinit var itemListViewAdapter: ItemListViewAdapter
    private lateinit var binding: FragmentViewListShoppingBinding
    private lateinit var viewModel: ListItemViewModel
    private lateinit var loadingDialog: AlertDialog

    private val itemRepositoryImpl = ItemRepositoryImpl()
    private val pagination = Pagination(itemRepositoryImpl)
    private val totalPages = pagination.TOTAL_NUM_ITEMS / pagination.ITEMS_PER_PAGE
    private var currentPage = 1

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
        viewModel.validationItemList()
        viewModel.loadItemData()
        toggleButtons()
        binding.apply {
            if (totalPages ==  0 && totalPages == 1) {
                tvPage.text = "$currentPage of 1"
            } else {
                tvPage.text = "$currentPage of ${totalPages + 1}"
            }
            backButtonToCreateItem.setOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            addFabItem.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_viewListShopping_to_addItem)
            }
            nextFabItem.setOnClickListener {
                currentPage++
                tvPage.text = "$currentPage of ${totalPages + 1}"
                subscribe()
                toggleButtons()
            }
            prevFabItem.setOnClickListener {
                currentPage--
                tvPage.text = "$currentPage of ${totalPages + 1}"
                subscribe()
                toggleButtons()
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
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = ItemRepositoryImpl()
                return ListItemViewModel(repo) as T
            }

        }).get(ListItemViewModel::class.java)
//        Log.i("INI VIEW LIST CHECK ITEMLIST", "${ItemRepositoryImpl.itemList}")
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
                    binding.apply {
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
            val paginationData = pagination.generatePage(currentPage)
            itemListViewAdapter.setItemList(paginationData)
//            Log.i("INI VIEW LIST LIHAT JUMLAH DATA", "DATA ITEMLIST ADA ${ItemRepositoryImpl.itemList.size}")
        })
        viewModel.itemUpdateLiveData.observe(this, {
            Navigation.findNavController(requireView()).navigate(R.id.action_viewListShopping_to_addItem, bundleOf("item_update" to it))
        })
    }

    private fun toggleButtons() {
        if ( (currentPage - 1) == totalPages && totalPages != 0) {
            binding.apply {
                nextFabItem.isEnabled = false
                prevFabItem.isEnabled = true
            }
        } else if (currentPage == 1  && totalPages != 0) {
            binding.apply {
                nextFabItem.isEnabled = true
                prevFabItem.isEnabled = false
            }
        } else if (currentPage >= 1 && currentPage <= totalPages) {
            binding.apply {
                nextFabItem.isEnabled = true
                prevFabItem.isEnabled = true
            }
        } else {
            binding.apply {
                nextFabItem.isEnabled = false
                prevFabItem.isEnabled = false
            }
        }
    }


    companion object {
        fun newInstance() = ViewListShoppingFragment()
    }
}