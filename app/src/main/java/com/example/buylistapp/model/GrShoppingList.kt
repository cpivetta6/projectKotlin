package com.example.buylistapp.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "GrShoppingList",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = GrUser::class,
            parentColumns = ["userid"],
            childColumns = ["ownerUserId"],
            onDelete = androidx.room.ForeignKey.CASCADE)
    ],
    indices = [Index(value= ["ownerUserId"])]
    )
data class GrShoppingList(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0, //PK
    var ownerUserId: String, //Foreign Key User
    var description: String,
    var category: String,
    var createdate: String
)
