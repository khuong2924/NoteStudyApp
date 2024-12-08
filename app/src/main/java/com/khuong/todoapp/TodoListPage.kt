package com.khuong.todoapp

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import java.util.Date
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.observeAsState(emptyList())
    var inputText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var isAdding by remember { mutableStateOf(false) }
    var isAscending by remember { mutableStateOf(true) }
    var showCalendar by remember { mutableStateOf(false) } // Điều khiển hiển thị lịch

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        // Header với hai nút hình chữ nhật
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, // Tạo khoảng cách đều giữa các nút
            verticalAlignment = Alignment.CenterVertically // Căn giữa các nút theo chiều dọc
        ) {
            // Nút Add Todo
            Button(
                onClick = { isAdding = true },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp) // Chiều cao nút đồng nhất
                    .padding(horizontal = 8.dp), // Tạo khoảng cách giữa các nút
                shape = RoundedCornerShape(16.dp), // Góc bo mềm mại hơn
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) // Màu nền nút chính
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo",
                    tint = Color.White // Màu biểu tượng
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text
                Text(
                    text = "",
                    color = Color.White,
                    fontSize = 16.sp // Kích thước chữ dễ đọc
                )
            }

            // Nút Sort Deadlines
            IconButton(
                onClick = {
                    viewModel.updateTodoList(sortDeadlines(todoList, isAscending))
                    isAscending = !isAscending
                },
                modifier = Modifier
                    .size(56.dp) // Đồng nhất kích thước với các nút khác
                    .padding(horizontal = 8.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Sort Deadlines",
                        tint = MaterialTheme.colorScheme.primary // Sử dụng màu chính để đồng nhất giao diện
                    )
                    Text(
                        text = "Sort",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary // Màu chữ đồng nhất
                    )
                }
            }


            // Nút View Deadlines
            Button(
                onClick = { showCalendar = true },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // Hiển thị form nhập liệu
        if (isAdding) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                TodoInputForm(
                    inputText = inputText,
                    onInputTextChanged = { inputText = it },
                    selectedDate = selectedDate,
                    onDateChanged = { selectedDate = it },
                    onSave = { title, deadline ->
                        viewModel.addTodo(title, deadline)
                        inputText = "" // reset inputText
                        selectedDate = null // reset selectedDate
                        isAdding = false // Đóng form nhập liệu
                    },
                    showCalendar = showCalendar, // Truyền tham số showCalendar
                    onDismissCalendar = { showCalendar = false } // Hàm đóng calendar
                )
            }
        }

        // Danh sách Todo
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            content = {
                itemsIndexed(todoList) { index, item ->
                    TodoItem(
                        item = item,
                        onDelete = { viewModel.deleteTodo(item.id) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 8.dp)
                    )
                }
            }
        )
    }

    // Hiển thị giao diện lịch khi showCalendar = true
    if (showCalendar) {
        DeadlineCalendar(todoList = todoList, onDismiss = { showCalendar = false })
    }
}


fun sortDeadlines(todoList: List<Todo>, isAscending: Boolean): List<Todo> {
    return if (isAscending) {
        todoList.sortedBy { it.deadline }
    } else {
        todoList.sortedByDescending { it.deadline }
    }
}

@Composable
fun TodoInputForm(
    inputText: String,
    onInputTextChanged: (String) -> Unit,
    selectedDate: Long?,
    onDateChanged: (Long?) -> Unit,
    onSave: (String, Long?) -> Unit,
    showCalendar: Boolean, // Nhận tham số showCalendar
    onDismissCalendar: () -> Unit // Nhận tham số để đóng calendar
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // TextField cho tiêu đề Todo với icon
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputTextChanged,
            label = { Text("Todo Title") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Todo Title Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .shadow(4.dp, RoundedCornerShape(12.dp)) // Thêm bóng đổ
        )

        // Hiển thị ngày đã chọn hoặc nút chọn ngày với icon
        Button(
            onClick = { showDatePicker = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
                .shadow(4.dp, RoundedCornerShape(30.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    tint = Color.White
                )
                Text(
                    text = if (selectedDate != null) {
                        SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date(selectedDate))
                    } else {
                        "Select Deadline"
                    },
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // DatePickerDialog
        if (showDatePicker) {
            val context = LocalContext.current
            val calendar = java.util.Calendar.getInstance()

            selectedDate?.let {
                calendar.timeInMillis = it
            }

            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val newCalendar = java.util.Calendar.getInstance()
                    newCalendar.set(year, month, dayOfMonth)
                    onDateChanged(newCalendar.timeInMillis)
                    showDatePicker = false
                },
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Nút lưu với hiệu ứng hover
        Button(
            onClick = {
                if (inputText.isNotEmpty()) {
                    onSave(inputText, selectedDate)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(30.dp))
                .shadow(8.dp, RoundedCornerShape(30.dp))
        ) {
            Text("Save", color = Color.White)
        }
    }

    // Hiển thị giao diện lịch khi showCalendar = true
    if (showCalendar) {
        DeadlineCalendar(todoList = listOf(), onDismiss = onDismissCalendar)
    }
}


@Composable
fun DeadlineCalendar(todoList: List<Todo>, onDismiss: () -> Unit) {
    // Lấy danh sách các ngày có deadline
    val deadlineDates = todoList.mapNotNull { it.deadline?.let { Date(it) } }
    // Sử dụng CalendarView để hiển thị lịch
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant) // Đặt màu nền đậm hơn
    ) {
        // Thêm tiêu đề cho lịch
        Text(
            text = "Deadline Calendar",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant) // Màu nền cho tiêu đề
                .padding(8.dp)
        )

        // Sử dụng Custom Calendar
        CalendarView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            datesWithDeadline = deadlineDates,
            onDateSelected = { selectedDate ->
                // Xử lý sự kiện khi người dùng chọn một ngày
                println("Selected date: $selectedDate")
            }
        )
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant) // Màu nền đậm cho nút
        ) {
            Text("Close Calendar", color = MaterialTheme.colorScheme.onSecondary)
        }
    }
}

@Composable
fun CalendarView(modifier: Modifier, datesWithDeadline: List<Date>, onDateSelected: (LocalDate) -> Unit) {
    // Đảm bảo kiểu LocalDate rõ ràng cho calendarState
    val calendarState = remember { mutableStateOf(LocalDate.now()) }

    // Lấy số ngày trong tháng hiện tại
    val daysInMonth = (1..calendarState.value.lengthOfMonth()).toList()

    // Chia các ngày thành tuần
    val weeks = daysInMonth.chunked(7)

    Column(modifier = modifier) {
        // Tiêu đề với tháng và năm
        Text(
            text = "${calendarState.value.month} ${calendarState.value.year}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp)
        )
        // LazyColumn với tuần ngày
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(weeks) { weekDays ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        weekDays.forEach { day ->
                            val dayDate = calendarState.value.withDayOfMonth(day)

                            val isDeadlineDay = datesWithDeadline.any {
                                it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() == dayDate
                            }

                            DayCell(day = day, isDeadlineDay = isDeadlineDay, onClick = { onDateSelected(dayDate) })
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DayCell(day: Int, isDeadlineDay: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$day",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDeadlineDay) Color.Red else Color.Black
        )
    }
}
@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant) // Màu nền nhạt hơn (surfaceVariant)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Hiển thị ngày deadline mà không có giờ
            item.deadline?.let {
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(it), // Chỉ hiển thị ngày
                    fontSize = 23.sp,  // Kích thước chữ lớn hơn
                    color = Color.Black
                )
            }
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}






