package com.pascal.irfaan.shoppinglist.presentations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.pascal.irfaan.shoppinglist.R
import com.pascal.irfaan.shoppinglist.utils.OnNavigationListener

class MainActivity : AppCompatActivity(), OnNavigationListener {

    private lateinit var addItem: AddItem
    private lateinit var createItemButton: Button
    private lateinit var viewListButton: Button
    private lateinit var viewListShopping: ViewListShopping

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addItem = AddItem()
        viewListShopping = ViewListShopping()
        createItemButton = findViewById(R.id.createItemButton)
        viewListButton = findViewById(R.id.viewListButton)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, viewListShopping).commit()
        createItemButton.setOnClickListener {
            switchFragment(addItem)
        }
        viewListButton.setOnClickListener {
            switchFragment(viewListShopping)
        }

    }

    override fun onFragmentSplash() {

    }

    fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}