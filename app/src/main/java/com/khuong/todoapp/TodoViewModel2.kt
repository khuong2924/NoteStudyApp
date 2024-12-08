package com.khuong.todoapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import java.util.*

class TodoViewModel2 : ViewModel() {
    // Các trạng thái giờ, phút, giây và AM/PM
    var hour = mutableStateOf("0")
    var minute = mutableStateOf("0")
    var second = mutableStateOf("0")
    var amOrPm = mutableStateOf("0")

    // Dùng mutableStateOf để lưu danh sách todo
    var todoList = mutableStateOf(listOf<String>())

    // Dùng mutableStateOf để điều khiển hiển thị form
    var showForm = mutableStateOf(false)

    // Coroutine scope để chạy đồng hồ
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    // Khởi tạo đồng hồ
    init {
        updateClock()
    }

    // Hàm cập nhật đồng hồ mỗi giây
    private fun updateClock() {
        coroutineScope.launch {
            while (true) {
                val cal = Calendar.getInstance()
                hour.value = cal.get(Calendar.HOUR).run {
                    if (this.toString().length == 1) "0$this" else "$this"
                }
                minute.value = cal.get(Calendar.MINUTE).run {
                    if (this.toString().length == 1) "0$this" else "$this"
                }
                second.value = cal.get(Calendar.SECOND).run {
                    if (this.toString().length == 1) "0$this" else "$this"
                }
                amOrPm.value = cal.get(Calendar.AM_PM).run {
                    if (this == Calendar.AM) "AM" else "PM"
                }
                delay(1000) // Cập nhật mỗi giây
            }
        }
    }

}
