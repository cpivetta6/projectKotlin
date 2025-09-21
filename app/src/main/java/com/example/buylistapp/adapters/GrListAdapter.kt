package com.example.buylistapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
//import androidx.compose.ui.semantics.text
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter // Importação importante
import androidx.recyclerview.widget.RecyclerView
import com.example.buylistapp.R
import com.example.buylistapp.model.GrShoppingList
import com.example.buylistapp.activity.DetailListActivity
import com.example.buylistapp.activity.MasterListActivity
import com.example.buylistapp.viewModel.MasterListViewModel
import java.util.Locale

// Interface para lidar com cliques de exclusão e edição,
// para que a Activity/Fragment possa interagir com o ViewModel.
interface ShoppingListInteractionListener {
    fun onDeleteClicked(list: GrShoppingList, position: Int)
    fun onEditClicked(list: GrShoppingList, position: Int, newName: String)
    // Você pode adicionar mais interações aqui se necessário (ex: onItemSelected)
}

class GrListAdapter(
    private val context: Context, // Context ainda pode ser útil para AlertDialogs, Intents
    private val interactionListener: MasterListActivity // Listener para interações
) : ListAdapter<GrShoppingList, GrListAdapter.GrListViewHolder>(GrShoppingListDiffCallback()) {
    // Não precisamos mais da MutableList 'lists' aqui, ListAdapter cuida disso.



    class GrListViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {
        val date: TextView = listView.findViewById(R.id.date)
        val description: TextView = listView.findViewById(R.id.description)
        val delete: TextView = listView.findViewById(R.id.delete)

        fun bind(list: GrShoppingList, context: Context, listener: MasterListActivity, position: Int) {
            // 'list' é o item atual fornecido pelo ListAdapter através do getItem(position)
            // Não modificamos mais o item diretamente aqui (ex: currentList.description.uppercase)
            // Essa transformação deve ser feita antes de submeter a lista ao adapter,
            // ou o ViewModel deve fornecer os dados já formatados.
            // Para este exemplo, vamos manter a transformação aqui, mas idealmente seria antes.

            val formattedDescription = list.description.uppercase(Locale.ROOT)

            date.text = list.createdate
            description.text = if (list.category.isBlank()) {
                "\uD83D\uDED2   $formattedDescription"  // 🛒 carrinho
            } else {
                "${list.category}   $formattedDescription"
            }

            //Delete LIST
            delete.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Delete list")
                    .setMessage("Are you sure?\nThis is not reversible")
                    .setPositiveButton("Delete") { _, _ ->
                        listener.onDeleteClicked(list, position)


                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }

            //Click para abrir detalhes
            itemView.setOnClickListener {
                val intent = Intent(context, DetailListActivity::class.java)
                // Se DetailListActivity precisa do ID da lista, passe-o aqui:
                // intent.putExtra("LIST_ID", list.id) // Supondo que GrShoppingList tenha um 'id'
                context.startActivity(intent)
            }

            //Evento de click LONGO para editar
            itemView.setOnLongClickListener{
                val editText = EditText(context)
                editText.setText(list.description) // Usar a descrição original para edição

                AlertDialog.Builder(context)
                    .setTitle("Editar nome da lista")
                    .setView(editText)
                    .setPositiveButton("Salvar") { _, _ ->
                        val newName = editText.text.toString().trim()
                        //if (newName.isNotEmpty()) {
                            listener.onEditClicked(list, position, newName)
                        //}
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_square, parent, false)
        return GrListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrListViewHolder, position: Int) {
        val currentList = getItem(position) // Usar getItem(position) do ListAdapter
        holder.bind(currentList, context, interactionListener, position)
    }

    // Não precisamos mais de getItemCount(), addList(), updateList(), removeList()
    // ListAdapter cuida da contagem e a atualização é feita via submitList()
    // A lógica de recuperarOwnerUserId() não pertence ao Adapter.
    // Ela deve estar no ViewModel ou Repository ao criar/adicionar uma nova lista.
}

// DiffUtil.ItemCallback para o ListAdapter
class GrShoppingListDiffCallback : DiffUtil.ItemCallback<GrShoppingList>() {
    override fun areItemsTheSame(oldItem: GrShoppingList, newItem: GrShoppingList): Boolean {
        // Supondo que GrShoppingList tenha um campo 'id' único.
        // Se não tiver, você precisa de uma forma de identificar unicamente um item.
        // Se 'description' e 'createdate' combinados forem únicos, você pode usá-los,
        // mas um ID é preferível.
        // Para este exemplo, vamos supor que a combinação de descrição e data de criação seja única
        // ou que você adicionará um ID à sua model GrShoppingList.
        // Se você tiver um ID: return oldItem.id == newItem.id
        return oldItem.description == newItem.description && oldItem.createdate == newItem.createdate
    }

    override fun areContentsTheSame(oldItem: GrShoppingList, newItem: GrShoppingList): Boolean {
        // Verifica se todos os campos relevantes são iguais.
        // O ListAdapter usa isso para saber se precisa redesenhar o item.
        return oldItem == newItem // Se GrShoppingList for uma data class, isso funciona bem.
        // Caso contrário, compare os campos individualmente:
        // oldItem.description == newItem.description &&
        // oldItem.category == newItem.category &&
        // oldItem.createdate == newItem.createdate && ...
    }
}