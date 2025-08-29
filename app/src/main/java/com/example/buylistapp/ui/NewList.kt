package com.example.buylistapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.buylistapp.JsonManager
import com.example.buylistapp.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buylistapp.model.GrItemAdapter
import com.example.buylistapp.model.GrItem


class NewList : AppCompatActivity() {
    @SuppressLint("MissingSuperCall")

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GrItemAdapter
    private val items = mutableListOf<GrItem>() // lista usada pelo adapter





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newListPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.container)
        adapter = GrItemAdapter(items, this, JsonManager.loadJson(this))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Funcao responsavel pelo swipe da Recycle view
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, // dragDirs (não vamos usar arrastar)
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // swipe para os dois lados
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Não vamos mexer com arrastar
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Aqui será chamado quando o usuário fizer swipe
                val position = viewHolder.bindingAdapterPosition
                //adapter.notifyItemRemoved(position)
                //items.removeAt(position)
                adapter.removeItem(position)


                Log.d("RecyclerView", "Item deslizado na posição: $position")
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Variables
        val btnMainPage = findViewById<Button>(R.id.btnBack)
        val btnAddProduct = findViewById<Button>(R.id.btnAddProduct)


        //Open Pop up
        btnAddProduct.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
            val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
            val editQuantity = dialogView.findViewById<EditText>(R.id.editQuantity)

            AlertDialog.Builder(this)
                .setTitle("Add Item")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->
                    val description = editDescription.text.toString()
                    val quantity = editQuantity.text.toString().toIntOrNull() ?: 0

                    if (description.isNotEmpty()) {
                        // salva no JSON
                        JsonManager.addItem(this, description, quantity)

                        // adiciona no RecyclerView
                        adapter.addItem(GrItem(description, quantity))
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        //Back button click
        btnMainPage.setOnClickListener{
            val intent = Intent(this, GroceryList::class.java)
            startActivity(intent)
        }


        // 👉 1. Carrega o JSON ao abrir a tela
        val root = JsonManager.loadJson(this)
        // 👉 2. Pega o usuário (exemplo: primeiro usuário da lista)
        val currentUser = root.users.firstOrNull()
        // 👉 3. Pega a lista de compras do usuário (exemplo: primeira lista dele)
        val currentList = currentUser?.lists?.firstOrNull()
        // 👉 4. Adiciona os itens dessa lista no RecyclerView
        currentList?.items?.let { listItems ->
            items.addAll(listItems)
            adapter.notifyDataSetChanged()
        }





    }
}