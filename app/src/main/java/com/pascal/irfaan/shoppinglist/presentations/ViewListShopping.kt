package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.networks.ItemAdapter


class ViewListShopping(val itemList: MutableList<Item>?) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerView = view!!.findViewById(R.id.recyclerItemList)
        return inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ItemAdapter(itemList)
        }

    }


    companion object {
        fun newInstance(itemList: MutableList<Item>) = ViewListShopping(itemList)
    }
}