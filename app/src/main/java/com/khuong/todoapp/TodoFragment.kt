package com.khuong.todoapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.khuong.todoapp.R


class TodoFragment : Fragment(R.layout.fragment_todo) {

    private lateinit var composeView: ComposeView
    private lateinit var view1Button: Button
    private lateinit var view2Button: Button
    private val viewModel: TodoViewModel by viewModels()
    private var currentView: Int by mutableStateOf(0) // 0: Không có gì hiển thị, 1: view1, 2: view2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lấy đối tượng ComposeView và Button từ layout
        composeView = view.findViewById(R.id.contentFrame)
        view1Button = view.findViewById(R.id.view1Button)
        view2Button = view.findViewById(R.id.view2Button)

        // Chỉ hiển thị các nút khi chưa nhấn bất kỳ nút nào
        composeView.visibility = View.GONE

        // Thiết lập nội dung của ComposeView một lần khi Fragment được tạo
        composeView.setContent {
            when (currentView) {
                1 -> TodoListPage(viewModel = viewModel)
                   2 -> MainActivity2Content() // Thay thế MainActivity2Content bằng view bạn muốn hiển thị
            }
        }

        // Đổi view khi nhấn button
        view1Button.setOnClickListener {
            currentView = 1
            composeView.visibility = View.VISIBLE // Hiển thị ComposeView khi nhấn view1Button
        }

        view2Button.setOnClickListener {
            currentView = 2
            composeView.visibility = View.VISIBLE // Hiển thị ComposeView khi nhấn view2Button
        }
    }
}





