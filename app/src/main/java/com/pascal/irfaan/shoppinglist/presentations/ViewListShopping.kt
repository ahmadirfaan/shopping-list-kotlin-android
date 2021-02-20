package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pascal.irfaan.shoppinglist.R


class ViewListShopping : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_list_shopping, container, false)
    }

    companion object {

        fun newInstance() = ViewListShopping()
    }
}