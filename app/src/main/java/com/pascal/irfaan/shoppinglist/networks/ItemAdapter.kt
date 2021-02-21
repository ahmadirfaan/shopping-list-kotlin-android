package com.pascal.irfaan.shoppinglist.networks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item

class ItemAdapter(private val itemList: MutableList<Item>?) : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {




    inner class ItemHolder(containerView : View) : RecyclerView.ViewHolder(containerView) {
        val tvShoppingDate = containerView.findViewById<TextView>(R.id.tvShoppingDate)
        val tvItemName = containerView.findViewById<TextView>(R.id.tvShoppingDate)
        val tvQuantity = containerView.findViewById<TextView>(R.id.tvQuantity)
        val tvNotes = containerView.findViewById<TextView>(R.id.tvNotes)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.layout_item, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item: Item = itemList!!.get(position)
        val tvShoppingDate = holder.tvShoppingDate
        val tvItemName = holder.tvItemName
        val tvQuantity = holder.tvQuantity
        val tvNotes = holder.tvNotes
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

}