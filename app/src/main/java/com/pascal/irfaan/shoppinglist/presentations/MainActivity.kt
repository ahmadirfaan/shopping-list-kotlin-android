package com.pascal.irfaan.shoppinglist.presentations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pascal.irfaan.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
