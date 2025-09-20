package com.example.buylistapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buylistapp.R
import com.example.buylistapp.model.GrShoppingList
import com.example.buylistapp.activity.DetailListActivity
import java.util.Locale


class GrListAdapter (
    private val lists: MutableList<GrShoppingList>,
    private val context: Context,

) :

  RecyclerView.Adapter<GrListAdapter.GrListViewHolder>() {
  class GrListViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {
      val date: TextView = listView.findViewById(R.id.date)
      val description: TextView = listView.findViewById(R.id.description)
      val delete: TextView = listView.findViewById(R.id.delete)

          //val createdat: TextView = listView.findViewById(R.id.createdat)
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_square, parent, false)
        return GrListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrListViewHolder, position: Int) {
        val currentList = lists[position] // 'lists' é a sua MutableList<GrShoppingList> no adapter

        currentList.description = currentList.description.uppercase(Locale.ROOT)

        holder.date.text = currentList.createdate
        if(currentList.category.isBlank()){
            holder.description.text = "\uD83D\uDED2   ${currentList.description}"  // 🛒 carrinho
        }else{
            holder.description.text = "${currentList.category}   ${currentList.description}"
        }


        //Delete LIST
        holder.delete.setOnClickListener {
            AlertDialog.Builder(context)
             .setTitle("Delete list")
             .setMessage("Are you sure?\nThis is not reversible")
                .setPositiveButton("Delete") { dialog, which ->
                    removeList(position)

            }
                .setNegativeButton("Cancel") {dialog, whitch ->
                    dialog.dismiss()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
                }



        //Click para abrir detalhes
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailListActivity::class.java)
            context.startActivity(intent)
        }

        //Evento de click LONGO
        holder.itemView.setOnLongClickListener{
            val editText = EditText(holder.itemView.context)
            editText.setText(currentList.description)

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Editar nome da lista")
                .setView(editText as View)
                .setPositiveButton("Salvar") { _, _ ->

                    val newName = editText.text.toString()
                    currentList.description = newName

                    //Update item
                    if (newName.isNotEmpty()) {
                        //
                        updateList(position,currentList)
                      }
                     }
                .setNegativeButton("Cancelar", null)
                .show()
            true

        }

        //holder.createdat.text = currentList.createdate
    }

    override fun getItemCount(): Int {
        //Log.d("GrListAdapter", "getItemCount: Tamanho da lista = ${lists.size}")
        return lists.size
    }

    fun addList(list: GrShoppingList) {
        //val sharedPreferences = context.getSharedPreferences("MinhasPreferenciasApp", Context.MODE_PRIVATE)
        val idDoUsuarioLogado = recuperarOwnerUserId()



        if (idDoUsuarioLogado == null) {
            // Usuário não está logado ou ID não encontrado.
            // Lidar com este caso (ex: redirecionar para login, mostrar erro).

        }else{
            list.ownerUserId = idDoUsuarioLogado
            //list.userId = idDoUsuarioLogado ?: ""
            lists.add(list)
            notifyItemInserted(lists.size - 1) // atualiza só o último
        }

    }

    // Função de exemplo para recuperar o ID do usuário
    private fun recuperarOwnerUserId(): String? { // Mudei para retornar String? para lidar com falha
        val sharedPreferences = context.getSharedPreferences("MinhasPreferenciasApp", Context.MODE_PRIVATE)
        //val sharedPreferences = getSharedPreferences("MinhasPreferenciasApp", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("ID_USUARIO_LOGADO", null)
        if (userId == null) {
            Log.e("MasterListActivity", "ID do usuário não encontrado nas SharedPreferences!")
            // Considere redirecionar para login ou mostrar um erro mais proeminente
        }
        return userId
    }

    private fun updateList(position: Int, newList: GrShoppingList) {
        //lists[position] = newList   // substitui o item na lista]
        //notifyItemChanged(position) // manda o RecyclerView redesenhar só aquele item
        //lists.clear()
        //lists.addAll(newLists)

    }

    private fun removeList(position: Int){
        lists.removeAt(position)
        notifyItemRemoved(position)

    }


}