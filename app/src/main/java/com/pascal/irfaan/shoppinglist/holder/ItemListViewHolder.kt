package com.pascal.irfaan.shoppinglist.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pascal.irfaan.shoppinglist.databinding.ItemLayoutRecyclerViewBinding
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.ItemClickListener

class ItemListViewHolder(val view: View, val clickListener: ItemClickListener) : RecyclerView.ViewHolder(view) {

    private val binding = ItemLayoutRecyclerViewBinding.bind(view)

    fun bind(item: Item) {
        binding.apply {
            tvItemName.text = item.itemName
            tvShoppingDate.text = item.shoppingDate
            tvQuantity.text = item.quantity
            tvNotes.text = item.notes
            buttonDelete.setOnClickListener {
                clickListener.onDelete(item)
            }
        }


    }

}