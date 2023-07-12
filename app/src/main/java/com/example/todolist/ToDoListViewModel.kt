package com.example.todolist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Item
import com.example.todolist.data.ItemDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoListViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(
        itemName: String,
        itemCount: String,
        done: Int,
        date: String
    ): Item {
        return Item(
            itemTitle = itemName,
            itemContent = itemCount,
            done = done,
            date = date
        )
    }

    fun isEntryValid(itemName: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    fun addNewItem(itemName: String, itemCount: String, done: Int, date: String) {
        val newItem = getNewItemEntry(itemName, itemCount, done, date)
        insertItem(newItem)
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun markDone(item: Item) {
        if (item.done > 0) {
            val newItem = item.copy(done = -1)
            updateItem(newItem)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun isItemDone(item: Item): Boolean {
        return (item.done > 0)
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemTitle: String,
        itemCount: String,
        done: Int, date: String
    ): Item {
        return Item(
            id = itemId,
            itemTitle = itemTitle,
            itemContent = itemCount,
            done = done,
            date = date
        )
    }


    fun updateItem(
        itemId: Int,
        itemTitle: String,
        itemCount: String,
        done: Int,
        date: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemTitle, itemCount, done, date)
        updateItem(updatedItem)

    }

    fun printDone(item: Item): String {
        return when (item.done) {
            1 -> "To Do"
            else -> "Done !!"
        }
    }

    fun asKToReList(item: Item): Int {
        return when (item.done > 0) {
            true -> View.GONE
            else -> View.VISIBLE
        }
    }

    fun reList(item: Item) {
        updateItem(item.id, item.itemTitle, item.itemContent, done = 1,item.date)
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        )
        return format.format(date)
    }
}


class ToDoListViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}