package com.khuong.todoapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private var _todoList = MutableLiveData<List<Todo>>()
    val todoList : LiveData<List<Todo>> = _todoList
    // Các trạng thái cho form nhập
    val inputText by mutableStateOf("")
    val selectedDate by mutableStateOf<Long?>(null)

    init {
        getAllTodo()
    }

    fun getAllTodo(){
        _todoList.value = TodoManager.getAllTodo().reversed()
    }

    fun addTodo(title : String, deadline: Long?) {
        TodoManager.addTodo(title, deadline)
        getAllTodo()
    }


    fun deleteTodo(id : Int){
        TodoManager.deleteTodo(id)
        getAllTodo()
    }
    fun updateTodoList(newList: List<Todo>) {
        _todoList.value = newList
    }
}