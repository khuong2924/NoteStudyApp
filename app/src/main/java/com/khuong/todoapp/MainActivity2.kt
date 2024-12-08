package com.khuong.todoapp

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import java.util.Locale


import com.khuong.todoapp.ui.theme.AlarmClockTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class MainActivity2 : ComponentActivity() {
    private val viewModel: TodoViewModel2 by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hour by remember { mutableStateOf("0") }
            var minute by remember { mutableStateOf("0") }
            var second by remember { mutableStateOf("0") }
            var amOrPm by remember { mutableStateOf("0") }

            LaunchedEffect(Unit) {
                while (true) {
                    val cal = Calendar.getInstance()
                    hour = cal.get(Calendar.HOUR).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    minute = cal.get(Calendar.MINUTE).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    second = cal.get(Calendar.SECOND).run {
                        if (this.toString().length == 1) "0$this" else "$this"
                    }
                    amOrPm = cal.get(Calendar.AM_PM).run {
                        if (this == Calendar.AM) "AM" else "PM"
                    }

                    delay(1000)
                }
            }

            AlarmClockTheme {
                Scaffold() {
                    Box(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight(fraction = 0.6f),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    AnalogClockComponent(
                                        hour = viewModel.hour.value.toInt(),
                                        minute = viewModel.minute.value.toInt(),
                                        second = viewModel.second.value.toInt()
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun MainActivity2Content() {
    var selectedTime by remember { mutableIntStateOf(5) }
    var isCountingDown by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableIntStateOf(selectedTime * 60) }

    // Biến cho giờ, phút, giây, AM/PM (giữ nguyên cho phần đồng hồ analog/digital)
    var hour by remember { mutableStateOf("0") }
    var minute by remember { mutableStateOf("0") }
    var second by remember { mutableStateOf("0") }
    var amOrPm by remember { mutableStateOf("0") }

    // Thời gian chuông reo (giây)
    var alarmInterval by remember { mutableStateOf(10) }  // Thời gian mặc định 10 giây

    val context = LocalContext.current

    // LaunchedEffect để cập nhật đồng hồ mỗi giây
    LaunchedEffect(Unit) {
        while (true) {
            val cal = Calendar.getInstance()
            hour = cal.get(Calendar.HOUR).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            minute = cal.get(Calendar.MINUTE).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            second = cal.get(Calendar.SECOND).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            amOrPm = cal.get(Calendar.AM_PM).run {
                if (this == Calendar.AM) "AM" else "PM"
            }

            delay(1000)  // Cập nhật mỗi giây
        }
    }

    LaunchedEffect(isCountingDown) {
        if (isCountingDown && remainingTime > 0) {
            while (remainingTime > 0) {
                delay(1000) // Đợi 1 giây
                remainingTime -= 1 // Giảm thời gian còn lại 1 giây

                // Kiểm tra nếu thời gian còn lại chia hết cho alarmInterval
                if (remainingTime % alarmInterval == 0) {
                    playAlarmSound(context)  // Phát chuông sau mỗi alarmInterval giây
                }

                // Khi thời gian còn lại là 0, phát chuông và dừng đếm ngược
                if (remainingTime == 0) {
                    playAlarmSound(context) // Phát chuông khi đếm ngược xong
                    isCountingDown = false // Dừng đếm ngược
                }
            }
        }
    }

    AlarmClockTheme {
        Scaffold() {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(top = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 250.dp),  // Giới hạn kích thước phần đồng hồ
                        contentAlignment = Alignment.Center
                    ) {
                        AnalogClockComponent(
                            hour = hour.toInt(),
                            minute = minute.toInt(),
                            second = second.toInt()
                        )
                    }

                    Spacer(modifier = Modifier.height(80.dp))  // Khoảng trống phía dưới đồng hồ

                    Text("Select Time: $selectedTime min")
                    TimeSlider(
                        selectedTime = selectedTime,
                        onTimeChanged = { newTime -> selectedTime = newTime }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Nhập thời gian chuông reo
                    OutlinedTextField(
                        value = alarmInterval.toString(),
                        onValueChange = { newValue ->
                            // Chuyển đổi giá trị nhập vào thành số nguyên
                            alarmInterval = newValue.toIntOrNull() ?: 10  // Đảm bảo rằng giá trị là số hợp lệ
                        },
                        label = { Text("Nhập thời gian (bằng giây)") },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    // Nút Start/Stop Countdown
                    Button(
                        onClick = {
                            if (isCountingDown) {
                                isCountingDown = false // Dừng đếm ngược
                            } else {
                                remainingTime = selectedTime * 60 // Cập nhật thời gian
                                isCountingDown = true // Bắt đầu đếm ngược
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCountingDown) Color.Red else Color(0xFF006400)
                        )
                    ) {
                        Text(if (isCountingDown) "Stop Countdown" else "Start Countdown")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    CountdownTimer(remainingTime = remainingTime)
                }
            }
        }
    }
}

@Composable
fun CountdownTimer(remainingTime: Int) {
    // Tính toán và hiển thị thời gian đếm ngược dưới dạng phút:giây
    val minutes = remainingTime / 60
    val seconds = remainingTime % 60
    Text(
        text = String.format(Locale.US, "%02d:%02d", minutes, seconds)
    )
}

@Composable
fun TimeSlider(selectedTime: Int, onTimeChanged: (Int) -> Unit) {
    // Slider với giá trị từ 1 đến 60 phút
    Slider(
        value = selectedTime.toFloat(),
        onValueChange = { newValue -> onTimeChanged(newValue.toInt()) },
        valueRange = 1f..60f,
        steps = 59,  // Số bước nhảy
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

fun playAlarmSound(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.chuongreo)
    mediaPlayer.start()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonComposable(
    title: String,
    content: @Composable () -> Unit,
    onNavigateBack: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            onNavigateBack?.let {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}



