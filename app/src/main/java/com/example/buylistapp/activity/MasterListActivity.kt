package com.example.buylistapp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buylistapp.R
import com.example.buylistapp.adapters.GrListAdapter
import com.example.buylistapp.adapters.GrSymbolAdapter
import com.example.buylistapp.model.GrShoppingList
import com.example.buylistapp.viewModel.MasterListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class MasterListActivity : AppCompatActivity() {
    @SuppressLint("MissingSuperCall")



    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: GrListAdapter
    private val lists = mutableListOf<GrShoppingList>()
    private val masterListViewModel: MasterListViewModel by viewModels()



    //Symbol
    private var selectedSymbolForNewList: String? = null // Para armazenar o símbolo escolhido

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_grocery_list)


        setupWindowInsets()



        val btnAddList = findViewById<Button>(R.id.btnNewList)
        val btnMainPage = findViewById<Button>(R.id.btnBack)

        recyclerView = findViewById(R.id.rvcontainer) // SUBSTITUA PELO ID CORRETO
        listAdapter = GrListAdapter(lists, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listAdapter




        //Add nova lista
        btnAddList.setOnClickListener {

            //DAQUI PARA BAIXO COLOCO NO VIEW MODEL?

            val dialogView = layoutInflater.inflate(R.layout.dialog_add_list, null)
            val titleList = dialogView.findViewById<TextView>(R.id.titleList)
            val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
            //val editCategory = dialogView.findViewById<EditText>(R.id.symbolsRecyclerViewDialog)



            //SYMBOL
            val symbolsRecyclerView: RecyclerView = dialogView.findViewById(R.id.symbolsRecyclerViewDialog)

            //Lista de simbolos
            val utf8Symbols = listOf(
                "🛒", "✈️", "🎁")

            val symbolAdapter = GrSymbolAdapter(utf8Symbols) { clickedSymbol ->
                selectedSymbolForNewList = clickedSymbol
            }

            symbolsRecyclerView.adapter = symbolAdapter
            symbolsRecyclerView.layoutManager = GridLayoutManager(this, 3) // Ou outro LayoutManager
            //SYMBOL

            titleList.text = (SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))


            AlertDialog.Builder(this)
                .setTitle("Nuova lista")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->

                    val ownerId = ""
                    if (ownerId == "ID_USUARIO_PADRAO_ERRO" || ownerId == null) {
                        //Log.e("MasterListViewModel", "Não foi possível obter o ID do usuário. Lista não adicionada.")
                        //Toast.makeText(this, "Erro ao identificar usuário. Tente novamente.", Toast.LENGTH_LONG).show()
                        return@setPositiveButton // Sai do listener do botão positivo
                    }



                    val descriptionFromEditText = editDescription.text.toString().trim()
                    var descriptionFromSymbol = selectedSymbolForNewList ?: "🛒" // Usa o símbolo ou um padrão
                    //descriptionFromSymbol = "$descriptionFromSymbol + test"



                    /* //Objeto passado para o recycler view
                    val newList = GrShoppingList(
                        description = descriptionFromEditText,
                        category = descriptionFromSymbol,
                        ownerUserId = ownerId,
                        createdate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    )*/

                    Log.e("MasterListViewModel", "Erro ao adicionar lista:  $descriptionFromEditText")

                    //View Model
                    masterListViewModel.addNewShoppingList(
                        description = editDescription.text.toString().trim(),
                        symbol = selectedSymbolForNewList ?: "🛒",

                    )


                    //Codigo do Adapter
                    /*
                    if (::listAdapter.isInitialized) {
                        Log.d("MasterListActivity", "Adapter está inicializado. Chamando adapter.addList().")
                        listAdapter.addList(newList) // <--- PONTO DE INTERESSE
                        // Se o log dentro de addList no adapter não aparece,
                        // ou esta linha não é chamada, ou há um problema ANTES dela.
                    } else {
                        Log.e("MasterListActivity", "ERRO CRÍTICO: Adapter NÃO foi inicializado!")
                        Toast.makeText(this, "Erro: Adapter não configurado.", Toast.LENGTH_SHORT).show()
                    }*/

                    // Persistir 'newList' no banco de dados Room aqui (usando ViewModel e Coroutine)
                    // viewModel.insertShoppingList(newList)


                }

                .setNegativeButton("Cancel", null)
                .show()
        }

        /*
        btnAddList.setOnClickListener{
            val intent = Intent(this, DetailListActivity::class.java)
            startActivity(intent)
        }*/





        btnMainPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




    }


    private fun setupWindowInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }






}