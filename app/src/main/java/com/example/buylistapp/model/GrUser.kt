package com.example.buylistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GrUser")
data class GrUser (
    @PrimaryKey(autoGenerate = false)
    var userid: String,
    //val lists: MutableList<GrShoppingList> = mutableListOf()
)
