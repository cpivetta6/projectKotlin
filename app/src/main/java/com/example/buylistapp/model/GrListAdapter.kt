package com.example.buylistapp.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import com.example.buylistapp.JsonManager
import com.example.buylistapp.R

class GrListAdapter (
    private val items: MutableList<GrItem>,
    private val context: Context,
    private val root: GrRoot) :

    RecyclerView.Adapter<GrItemAdapter.GrItemViewHolder>() {







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