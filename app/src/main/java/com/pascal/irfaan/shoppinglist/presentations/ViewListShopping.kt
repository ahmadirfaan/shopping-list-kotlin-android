package com.pascal.irfaan.shoppinglist.presentations

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.adapter.ItemListViewAdapter
import com.pascal.irfaan.shoppinglist.databinding.FragmentViewListShoppingBinding
import com.pascal.irfaan.shoppinglist.utils.LoadingDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import com.pascal.irfaan.shoppinglist.viewmodel.ItemViewModel


class ViewListShopping() : Fragment() {

    private lateinit var itemListViewAdapter: ItemListViewAdapter
    private lateinit var binding: FragmentViewListShoppingBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: ItemViewModel
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewmodel()
        binding = FragmentViewListShoppingBinding.inflate(layoutInflater)
        itemListViewAdapter = ItemListViewAdapter(viewModel)

        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
        loadingDialog = LoadingDialog.build(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel.validationItemList()
        binding.apply {
            backButtonToCreateItem.setOnClickListener {
                view?.findNavController()?.popBackStack()
            }
            itemListRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = itemListViewAdapter
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("INI FRAGMENT VIEW LIST", "INI ON PAUSE")
    }

    override fun onResume() {
        super.onResume()
        Log.i("INI FRAGMENT VIEW LIST", "INI ON resume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("INI FRAGMENT VIEW LIST", "INI ON DESTROY")
    }


    private fun initViewmodel() {
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.validationItemList.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), "LIST ITEM DENGAN JUMLAH ${viewModel.getItemList().size}", Toast.LENGTH_LONG).show()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                }
            }
        })
        viewModel.itemListLiveData.observe(this, {
            itemListViewAdapter.setItemList(it)
        })
    }



    companion object {
        fun newInstance() = ViewListShopping()
    }
}