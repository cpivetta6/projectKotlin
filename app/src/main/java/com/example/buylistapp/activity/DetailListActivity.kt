package com.example.buylistapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buylistapp.R
import androidx.recyclerview.widget.RecyclerView
import com.example.buylistapp.adapters.GrItemAdapter
import com.example.buylistapp.model.GrItem


class DetailListActivity : AppCompatActivity() {
    @SuppressLint("MissingSuperCall")

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GrItemAdapter
    private val items = mutableListOf<GrItem>() // lista usada pelo adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_itemlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newListPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnBack = findViewById<Button>(R.id.btnBack)


        btnBack.setOnClickListener{
            val intent = Intent(this, MasterListActivity::class.java)
            startActivity(intent)
        }




    }
}