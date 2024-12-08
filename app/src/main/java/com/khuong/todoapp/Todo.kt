package com.khuong.todoapp

import java.time.Instant
import java.util.Date

data class Todo(
    var id: Int,
    var title: String,
    var createdAt: Date,
    val deadline: Long? = null // deadline có thể null nếu không có
)


fun getFakeTodo() : List<Todo> {
    return listOf(
        Todo(1, "First todo", Date.from(Instant.now()), Date.from(Instant.now()).time + 86400000), // Deadline 1 ngày sau
        Todo(2, "Second todo", Date.from(Instant.now()), Date.from(Instant.now()).time + 172800000), // Deadline 2 ngày sau
        Todo(3, "Third todo", Date.from(Instant.now()), null), // Không có deadline
        Todo(4, "Fourth todo", Date.from(Instant.now()), Date.from(Instant.now()).time + 259200000) // Deadline 3 ngày sau
    )
}



