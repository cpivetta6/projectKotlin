package com.example.buylistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "GrItem",
        foreignKeys = [
            androidx.room.ForeignKey(
                entity = GrShoppingList::class,
                parentColumns = ["id"],
                childColumns = ["shoppingListId"],
                onDelete = androidx.room.ForeignKey.CASCADE)
        ],
        indices = [Index(value= ["shoppingListId"])]
            )
data class GrItem (
    @PrimaryKey(autoGenerate = true)
    var id: Int, //PK
    var shoppingListId : Int,//Foreign Key List
    var description: String,
    var quantity: Int

)