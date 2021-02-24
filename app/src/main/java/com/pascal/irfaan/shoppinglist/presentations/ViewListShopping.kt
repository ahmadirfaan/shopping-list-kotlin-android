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
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.databinding.FragmentViewListShoppingBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.LoadingDialog
import com.pascal.irfaan.shoppinglist.utils.ResourceStatus
import com.pascal.irfaan.shoppinglist.viewmodel.ItemViewModel


class ViewListShopping() : Fragment() {

    private lateinit var binding: FragmentViewListShoppingBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: ItemViewModel
    private lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewmodel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
        loadingDialog = LoadingDialog.build(requireContext())
        binding = FragmentViewListShoppingBinding.inflate(layoutInflater)
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

    private fun viewListToString(itemList: MutableList<Item>): String {
        var stringBuilder = StringBuilder()
        for ((index, item) in itemList.withIndex()) {
            stringBuilder.append(
                "${index + 1}. Date Transaction : ${item.shoppingDate}, " +
                        "Item Name : ${item.itemName}, " +
                        "Quantity : ${item.quantity}, " +
                        "Notes : ${item.notes} "
            )
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    private fun initViewmodel() {
        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.item.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                    binding.viewListItem.text = ""
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.viewListItem.text = viewListToString(it.data as MutableList<Item>)
                    Toast.makeText(requireContext(), "LIST ITEM DENGAN JUMLAH ${viewModel.itemList.size}", Toast.LENGTH_LONG).show()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    binding.viewListItem.text = "DATA ITEM BELUM ADA"
                }
            }
        })
    }


    companion object {
        fun newInstance() = ViewListShopping()
    }
}