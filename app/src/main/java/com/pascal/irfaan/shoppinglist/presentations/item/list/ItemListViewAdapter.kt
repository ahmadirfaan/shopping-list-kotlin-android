package com.pascal.irfaan.shoppinglist.presentations.item.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.presentations.item.list.ItemListViewHolder
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener

class ItemListViewAdapter(val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ItemListViewHolder>() {

    private var items = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout_recycler_view,
        parent, false)
        return ItemListViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val product = items[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int =  items.size

    fun setItemList(newItemList : List<Item>) {
        items.clear()
        items.addAll(newItemList)
        notifyDataSetChanged()
    }

}