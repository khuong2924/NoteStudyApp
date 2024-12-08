package com.khuong.todoapp.ui

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur.NORMAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = BlurMaskFilter(blurRadius.toPx(), NORMAL)
        }
        frameworkPaint.color = color.toArgb()

        val leftPixel = offsetX.toPx()
        val topPixel = offsetY.toPx()
        val rightPixel = size.width + leftPixel
        val bottomPixel = size.height + topPixel

        canvas.drawRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            paint = paint,
        )
    }
}


fun Modifier.shadowCircular(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp
): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = BlurMaskFilter(blurRadius.toPx(), NORMAL)
        }
        frameworkPaint.color = color.toArgb()

        canvas.drawCircle(
            center = Offset(x = center.x + offsetX.toPx(), y = center.y + offsetY.toPx()),
            radius = size.width / 2,
            paint = paint
        )
    }
}


@Composable
fun ShadowPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /* Handle click */ },
            modifier = Modifier
                .size(150.dp)
                .shadowCircular(
                    color = Color.Gray,
                    offsetX = 4.dp,
                    offsetY = 4.dp,
                    blurRadius = 10.dp
                )
        ) {
            Text("Shadow Circular")
        }
    }
}

@Composable
fun ShadowRectPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .shadow(
                    color = Color.Gray,
                    offsetX = 6.dp,
                    offsetY = 6.dp,
                    blurRadius = 15.dp
                )
                .background(Color.Blue)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewShadows() {
//    Box {
//        ShadowPreview()
//        ShadowRectPreview()
//    }
//}