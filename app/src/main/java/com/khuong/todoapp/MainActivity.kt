package com.khuong.todoapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.khuong.todoapp.ui.theme.TodoAppTheme
import com.khuong.todoapp.TodoFragment

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AndroidView(factory = { context ->
                        val frameLayout = FrameLayout(context)
                        frameLayout.id = FrameLayout.generateViewId()

                        // Replace the fragment dynamically
                        supportFragmentManager.beginTransaction()
                            .replace(frameLayout.id, TodoFragment())
                            .commit()

                        frameLayout
                    })
                }
            }
        }
    }
}
