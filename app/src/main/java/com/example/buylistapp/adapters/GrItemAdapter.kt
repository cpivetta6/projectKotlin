package com.example.buylistapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.buylistapp.R
import com.example.buylistapp.activity.DetailListActivity
import com.example.buylistapp.model.GrItem
import com.google.android.material.checkbox.MaterialCheckBox
import java.util.Locale
import kotlin.experimental.inv

// A interface está correta.
interface ItemInteractionListener {
    fun onDeleteClicked(list: GrItem, position: Int)
    fun onEditClicked(list: GrItem, position: Int, newName: String)
    fun onItemCheckedChanged(item: GrItem, isChecked: Boolean)
}

// O Adapter agora só depende da interface, o que é uma prática melhor.
class GrItemAdapter(
    private val interactionListener: ItemInteractionListener
) : ListAdapter<GrItem, GrItemAdapter.GrItemViewHolder>(GrItemDiffCallback()) {

    // O ViewHolder agora é uma classe interna.
    // Ele recebe a interface, não a Activity concreta.
    class GrItemViewHolder(
        itemView: View,
        private val listener: ItemInteractionListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val description: TextView = itemView.findViewById(R.id.tvDescription)
        private val amount: TextView = itemView.findViewById(R.id.amount)
        private val checkBox: MaterialCheckBox = itemView.findViewById(R.id.checkBox)

        // Variável para armazenar o item que este ViewHolder está exibindo.
        private var currentItem: GrItem? = null

        // O listener é configurado apenas UMA VEZ quando o ViewHolder é criado.
        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                // Usa a variável 'currentItem' que foi definida no último 'bind'.
                // O 'let' protege contra cliques antes do primeiro bind.
                currentItem?.let { item ->
                    // Esta verificação previne chamadas redundantes ao ViewModel.
                    if (item.isChecked != isChecked) {
                        // Notifica o ViewModel sobre a ação do usuário.
                        listener.onItemCheckedChanged(item, isChecked)
                    }
                }
            }
        }

        // O método bind agora é limpo e sua única função é ATUALIZAR os dados da View.
        fun bind(item: GrItem) {
            // PASSO CRUCIAL: Atualiza a referência do item atual.
            this.currentItem = item

            description.text = item.description.uppercase(Locale.ROOT)
            amount.text = item.amount.toString()

            // Define o estado do checkbox com base nos dados do item.
            checkBox.isChecked = item.isChecked

            // Aplica o estado visual (risco) com base nos dados do item.
            applyCheckedState(item.isChecked)
        }

        private fun applyCheckedState(isChecked: Boolean) {
            if (isChecked) {
                description.paintFlags = description.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                description.setTextColor(Color.GRAY)
                amount.setTextColor(Color.GRAY)
            } else {
                description.paintFlags = description.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                description.setTextColor(Color.WHITE)
                amount.setTextColor(Color.WHITE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        // Passe a interface do listener para o ViewHolder.
        return GrItemViewHolder(view, interactionListener)
    }

    override fun onBindViewHolder(holder: GrItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        // Chame o método bind simplificado do ViewHolder.
        holder.bind(currentItem)
    }
}

// DiffUtil.ItemCallback permanece o mesmo.
class GrItemDiffCallback : DiffUtil.ItemCallback<GrItem>() {
    override fun areItemsTheSame(oldItem: GrItem, newItem: GrItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GrItem, newItem: GrItem): Boolean {
        return oldItem == newItem
    }
}







