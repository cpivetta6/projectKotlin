package com.example.buylistapp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.util.Log
import android.widget.EditText
import com.example.buylistapp.JsonManager
import com.example.buylistapp.R


class GrItemAdapter(
    private val items: MutableList<GrItem>,
    private val context: Context,
    private val root: GrRoot) :

    RecyclerView.Adapter<GrItemAdapter.GrItemViewHolder>() {



    class GrItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return GrItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrItemViewHolder, position: Int) {
        val item = items[position]
        var newDesc :String = ""

        holder.tvDescription.text = item.description
        holder.tvQuantity.text = item.quantity.toString()

        //  aqui entram os eventos
        holder.tvDescription.setOnLongClickListener {
            val editText = EditText(holder.itemView.context)
            editText.setText(item.description)

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Editar descrição")
                .setView(editText as View)
                .setPositiveButton("Salvar") { _, _ ->
                     newDesc = editText.text.toString()

                    //Update item
                        if (newDesc.isNotEmpty()) {
                            JsonManager.updateItem(
                                context,
                                oldDescription = item.description,
                                newDescription = newDesc

                            )

                            // Atualiza o RecyclerView também
                            updateItem(position, GrItem( newDesc, item.quantity))


                        //notifyItemChanged(position) // atualiza só este item
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()

            true
        }
    }


    override fun getItemCount(): Int = items.size

    // função para adicionar item na lista e atualizar tela
    fun addItem(item: GrItem) {
        items.add(item)
        notifyItemInserted(items.size - 1) // atualiza só o último
    }

    fun updateItem(position: Int, newItem: GrItem) {
        items[position] = newItem   // substitui o item na lista
        notifyItemChanged(position) // manda o RecyclerView redesenhar só aquele item
    }

    fun removeItem(position: Int){
        //Log.d("RecyclerView", "REMOVO POS: $position")
        val item = items[position]

        JsonManager.removeItem(context, item.description)
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}

