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
fun TridimensionalCylinder() {
    var rotationState by remember { mutableStateOf(Offset(0f, 0f)) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(yellow)
        .padding(16.dp)
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, _, _ ->
                rotationState = Offset(
                    rotationState.x + pan.x, rotationState.y + pan.y
                )
            }
        }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            val cylinderRadius = canvasWidth / 4
            val cylinderHeight = canvasHeight / 2

            val rotatedPoints = calculateRotatedPoints(
                rotationState, cylinderRadius, cylinderHeight, centerX, centerY
            )

            val lines = mutableListOf<Pair<Int, Int>>()
            for (i in 0 until rotatedPoints.size / 2) {
                val nextIndex = (i + 1) % (rotatedPoints.size / 2)
                lines.add(Pair(i, nextIndex))
                lines.add(Pair(i + rotatedPoints.size / 2, nextIndex + rotatedPoints.size / 2))
                lines.add(Pair(i, i + rotatedPoints.size / 2))
            }

            lines.forEach { line ->
                drawLine(
                    color = Color.Black,
                    start = rotatedPoints[line.first],
                    end = rotatedPoints[line.second],
                    strokeWidth = 2f
                )
            }
        }
    }
}

private fun calculateRotatedPoints(
    rotation: Offset, radius: Float, height: Float, centerX: Float, centerY: Float
): List<Offset> {
    val rotationX = rotation.x * PI / 180
    val rotationY = rotation.y * PI / 180

    val points = mutableListOf<Offset>()
    val baseVertices = 30

    for (i in 0 until baseVertices) {
        val angle = 2 * PI * i / baseVertices
        val x = radius * cos(angle)
        val z = radius * sin(angle)
        val y = height / 2

        val rotatedX = x * cos(rotationY) - z * sin(rotationY)
        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ =
            x * sin(rotationY) + z * cos(rotationY) + y * sin(rotationX) + z * cos(rotationX)

        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    for (i in 0 until baseVertices) {
        val angle = 2 * PI * i / baseVertices
        val x = radius * cos(angle)
        val z = radius * sin(angle)
        val y = -height / 2

        val rotatedX = x * cos(rotationY) - z * sin(rotationY)
        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ =
            x * sin(rotationY) + z * cos(rotationY) + y * sin(rotationX) + z * cos(rotationX)

        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    return points
}
