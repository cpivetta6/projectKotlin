package com.example.buylistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GrRoot")
data class GrRoot(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //val users: MutableList<GrUser> = mutableListOf()
)
