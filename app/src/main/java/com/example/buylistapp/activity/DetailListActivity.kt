package com.example.buylistapp.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buylistapp.R
import androidx.recyclerview.widget.RecyclerView
import com.example.buylistapp.adapters.GrItemAdapter
import com.example.buylistapp.adapters.GrListAdapter
import com.example.buylistapp.adapters.ItemInteractionListener
import com.example.buylistapp.factory.DetailListViewModelFactory
import com.example.buylistapp.model.GrItem
import com.example.buylistapp.viewModel.DetailListViewModel
import com.example.buylistapp.viewModel.MasterListViewModel
import kotlinx.coroutines.launch


class DetailListActivity : AppCompatActivity(), ItemInteractionListener {
    @SuppressLint("MissingSuperCall")

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: GrItemAdapter
    private val items = mutableListOf<GrItem>() // lista usada pelo adapter
    //private val detailListViewModel: DetailListViewModel by viewModels()
    private val detailListViewModel: DetailListViewModel by viewModels {
        DetailListViewModelFactory(application) // Passe a application para a factory
    }

    // Companion object para definir constantes, como a chave do Intent extra
    companion object {
        const val EXTRA_LIST_ID = "com.example.buylistapp.LIST_ID" // Use um nome único e descritivo
    }

    private var currentListId: Int = -1 // Variável para armazenar o ID da lista


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_itemlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newListPage)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Recuperar o ID da lista do Intent
        val listIdFromIntent = intent.getIntExtra(EXTRA_LIST_ID, -1) // -1 é um valor padrão se o ID não for encontrado
        detailListViewModel.loadItemsForList(listIdFromIntent)
        val btnAddItem = findViewById<Button>(R.id.btnAddProduct)
        val btnBack = findViewById<Button>(R.id.btnBack)

        recyclerView = findViewById(R.id.container) // SUBSTITUA PELO ID CORRETO
        itemAdapter = GrItemAdapter( this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        // Observar o StateFlow do ViewModel para atualizar a lista
        lifecycleScope.launch {
            detailListViewModel.itemsForLoadedList.collect { itemsList ->
                // Este bloco é chamado IMEDIATAMENTE quando há um valor no StateFlow
                // (ou assim que o StateFlow recebe seu primeiro valor do DB via ViewModel)
                // e também sempre que o StateFlow é atualizado.
                itemAdapter.submitList(itemsList) // Passa a lista para o ListAdapter
            }
        }

        //Add new Item
        btnAddItem.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
            //val titleList = dialogView.findViewById<TextView>(R.id.titleList)

            val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
            val editAmount = dialogView.findViewById<EditText>(R.id.editQuantity)

            AlertDialog.Builder(this)
                .setTitle("Nuovo Prodotto")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->

                    val descriptionFromEditText = editDescription.text.toString().trim()
                    val amountFromEditText = editAmount.text.toString().trim().toIntOrNull() ?: 0

                    detailListViewModel.addNewItem(
                        listId = listIdFromIntent,
                        description = descriptionFromEditText,
                        amount = amountFromEditText)

                }

                .setNegativeButton("Cancel", null)
                .show()

        }

        btnBack.setOnClickListener{
            val intent = Intent(this, MasterListActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDeleteClicked(list: GrItem, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onEditClicked(list: GrItem, position: Int, newName: String) {
        TODO("Not yet implemented")
    }

    override fun onItemCheckedChanged(item: GrItem, isChecked: Boolean) {
        detailListViewModel.updateItemCheckedState(item, isChecked)
    }

}