package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.models.Item
import com.pascal.irfaan.shoppinglist.utils.OnNavigationListener

class MainActivity : AppCompatActivity(), OnNavigationListener {

    private lateinit var addItem: AddItem
    private lateinit var createItemButton: Button
    private lateinit var viewListButton: Button
    private lateinit var viewListShopping: ViewListShopping

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addItem = AddItem.newInstance(this)
        viewListShopping = ViewListShopping(null)
        createItemButton = findViewById(R.id.createItemButton)
        viewListButton = findViewById(R.id.viewListButton)
        createItemButton.setOnClickListener {
            switchFragment(addItem)
        }
        viewListButton.setOnClickListener {
            switchFragment(viewListShopping)
        }

    }

    override fun addShop(itemList: MutableList<Item>) {
        Log.i("INI INTERFACE DI MAIN ACTIVITY", "MASUKK DONG PLISSS")
        viewListShopping = ViewListShopping.newInstance(itemList)
    }

    fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}