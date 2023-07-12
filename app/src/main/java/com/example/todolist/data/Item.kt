package com.example.todolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val itemTitle: String,
    @ColumnInfo(name = "content")
    val itemContent: String,
    @ColumnInfo(name = "done")
    val done: Int,
    @ColumnInfo(name = "date")
    val date: String,
)

