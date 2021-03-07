package com.pascal.irfaan.shoppinglist.presentations.item.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.databinding.ItemLayoutRecyclerViewBinding
import com.pascal.irfaan.shoppinglist.data.models.Item
import com.pascal.irfaan.shoppinglist.data.models.ItemsEntity
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener

class ItemListViewHolder(val view: View, val listener : ItemClickListener) : RecyclerView.ViewHolder(view) {

    private val binding = ItemLayoutRecyclerViewBinding.bind(view)

    fun bind(item: ItemsEntity) {
        val dateString = item.dateShop.toString().substring(0,10)
        val timeString = item.dateShop.toString().substring(11,19)
        binding.apply {
            tvItemName.text = "Item Name : ${item.itemName}"
            tvShoppingDate.text = "Date : $dateString, Time : $timeString"
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