package com.pascal.irfaan.shoppinglist.presentations.item.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.databinding.ItemLayoutRecyclerViewBinding
import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener

class ItemListViewHolder(val view: View, val listener : ItemClickListener) : RecyclerView.ViewHolder(view) {

    private val binding = ItemLayoutRecyclerViewBinding.bind(view)

    fun bind(item: Item) {
        binding.apply {
            tvItemName.text = "Item Name : ${item.itemName}"
            tvShoppingDate.text = "Date : ${item.shoppingDate}"
            tvQuantity.text = "Quantity : ${item.quantity}"
            tvNotes.text = "Note : ${item.notes}"
            buttonDelete.setOnClickListener {
                listener.onDelete(item)
            }
            buttonUpdateItem.setOnClickListener {
                listener.onUpdate(item)
            }
        }


    }

}