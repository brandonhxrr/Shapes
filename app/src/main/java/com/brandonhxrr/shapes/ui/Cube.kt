package com.brandonhxrr.shapes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.brandonhxrr.shapes.ui.theme.yellow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TridimensionalCube(rotationState: Offset) {

    var currentRotationState by remember { mutableStateOf(rotationState) }

    Column(
        modifier = Modifier
            .background(yellow)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    currentRotationState = Offset(
                        currentRotationState.x + pan.x,
                        currentRotationState.y + pan.y
                    )
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            val cubeSize = canvasWidth / 2

            val rotatedPoints = calculateRotatedPoints(currentRotationState, cubeSize, centerX, centerY)

            val lines = listOf(
                Pair(0, 1),
                Pair(1, 3),
                Pair(2, 3),
                Pair(2, 0),
                Pair(4, 5),
                Pair(5, 7),
                Pair(6, 7),
                Pair(6, 4),
                Pair(0, 4),
                Pair(1, 5),
                Pair(2, 6),
                Pair(3, 7),
            )
            val colors = mapOf(
                "Blue" to Color.Blue,
                "Red" to Color.Red,
                "Green" to Color.Green,
                "White" to Color.White,
                "Cyan" to Color.Cyan,
                "Magenta" to Color.Magenta,
                "Gray" to Color.Gray,
                "LightGray" to Color.LightGray,
                "DarkGray" to Color.DarkGray
            )

            val indices = mapOf(
                0 to "Blue",
                1 to "Red",
                2 to "Green",
                3 to "White",
                4 to "Cyan",
                5 to "Magenta",
                6 to "Gray",
                7 to "LightGray",
                8 to "DarkGray"
            )

            lines.forEach { line ->
                drawLine(
                    color = colors[indices[line.first]]!!,
                    start = rotatedPoints[line.first],
                    end = rotatedPoints[line.second],
                    strokeWidth = 2f
                )
            }
        }
    }
}

private fun calculateRotatedPoints(
    rotation: Offset,
    size: Float,
    centerX: Float,
    centerY: Float
): List<Offset> {
    val rotationX = rotation.x * PI / 180
    val rotationY = rotation.y * PI / 180

    val points = mutableListOf<Offset>()
    for (i in 0 until 8) {
        val x = if (i and 1 == 0) size / 2 else -size / 2
        val y = if (i and 2 == 0) size / 2 else -size / 2
        val z = if (i and 4 == 0) size / 2 else -size / 2

        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ = y * sin(rotationX) + z * cos(rotationX)

        val rotatedX = x * cos(rotationY) + rotatedZ * sin(rotationY)
        val rotatedZFinal = -x * sin(rotationY) + rotatedZ * cos(rotationY)

        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    return points
}