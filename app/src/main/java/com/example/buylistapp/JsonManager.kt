package com.example.buylistapp

import android.content.Context
import android.util.Log
import com.example.buylistapp.model.GrItem
import com.example.buylistapp.model.GrRoot
import com.example.buylistapp.model.GrShoppingList
import com.example.buylistapp.model.GrUser
import com.google.gson.Gson
import java.io.File

object JsonManager {

    /*
    private const val FILENAME = "data.json"

    // Lê o JSON e retorna o Root ou cria um vazio se não existir
    fun loadJson(context: Context): GrRoot {
        val file = File(context.filesDir, FILENAME)
        Log.d("JsonManager", "Procurando arquivo: ${file.absolutePath}")

        if (!file.exists()) {
            // Cria JSON inicial vazio
            Log.d("JsonManager", "Arquivo não existe, criando JSON inicial")
            val initialJson = GrRoot(users = mutableListOf())
            saveJson(context, initialJson)
            return initialJson
        }
        Log.d("JsonManager", "Arquivo encontrado, lendo conteúdo")
        val jsonString = file.readText()
        Log.d("JsonManager", "Conteúdo do JSON: $jsonString")


        return Gson().fromJson(jsonString, GrRoot::class.java)
    }

    // Salva o objeto Root no arquivo
    private fun saveJson(context: Context, root: GrRoot) {
        val jsonString = Gson().toJson(root)
        val file = File(context.filesDir, FILENAME)
        file.writeText(jsonString)
    }


    fun addItem (context: Context, description: String, quantity: Int){

        Log.d("JsonManager", "Entro aqui")
        val root = JsonManager.loadJson(context)
        val user = root.users.find { it.userid == "cbissolo" }
        val targetList = user?.lists?.find { it.listid == "adqxaftt" }

        targetList?.items?.add(GrItem(description, quantity))

        JsonManager.saveJson(context, root)
    }

    fun updateItem (context: Context, oldDescription: String, newDescription: String){

        Log.d("JsonManager", "Update item")
        val root = JsonManager.loadJson(context)
        val user = root.users.find { it.userid == "cbissolo" }
        val targetList = user?.lists?.find { it.listid == "adqxaftt" }

        val item = targetList?.items?.find { it.description == oldDescription }
        item?.description = newDescription

        JsonManager.saveJson(context, root)
    }

    fun removeItem(context: Context, description: String) {
        val root = JsonManager.loadJson(context)
        val user = root.users.find { it.userid == "cbissolo" }
        val targetList = user?.lists?.find { it.listid == "adqxaftt" }

        // procura item pelo description
        val itemToRemove = targetList?.items?.find { it.description == description }

        if (itemToRemove != null) {
            targetList.items.remove(itemToRemove) // remove da lista
            saveJson(context, root)              // salva JSON atualizado
            //Log.d("JsonManager", "Item removido: $description")
        }

    }


*/
    }