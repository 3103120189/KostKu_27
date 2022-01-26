package com.example.kost_ku_27.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kost (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
    )
