package com.pascal.irfaan.shoppinglist.presentations.item.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.data.models.ItemsEntity
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener

class ItemListViewAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ItemListViewHolder>() {

    private var data : List<ItemsEntity> = ArrayList<ItemsEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_layout_recycler_view,
        parent, false)
        return ItemListViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val product = data[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int =  data.size

    fun setItemList(newItemList : List<ItemsEntity>) {
        this.data = newItemList
        notifyDataSetChanged()
    }

}