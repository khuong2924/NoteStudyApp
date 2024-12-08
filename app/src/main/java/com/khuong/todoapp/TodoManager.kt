package com.khuong.todoapp

import java.time.Instant
import java.util.Date

object TodoManager {

    private val todoList = mutableListOf(
        Todo(1, "Prepare presentation for meeting", Date.from(Instant.now()))
    )


    fun getAllTodo() : List<Todo>{
        return todoList
    }

    fun addTodo(title : String, deadline: Long?) {
        todoList.add(Todo(System.currentTimeMillis().toInt(), title, Date.from(Instant.now()), deadline))
    }



    fun deleteTodo(id : Int){
        todoList.removeIf{
            it.id==id
        }
    }
}