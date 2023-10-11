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
fun TridimensionalCylinder(rotation: Offset) {
    var currentRotationState by remember { mutableStateOf(rotation) }

    Column(modifier = Modifier
        .background(yellow)
        .padding(16.dp)
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, _, _ ->
                currentRotationState = Offset(
                    currentRotationState.x + pan.x, currentRotationState.y + pan.y
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
                currentRotationState, cylinderRadius, cylinderHeight, centerX, centerY
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
    val rotationX = rotation.y * PI / 180  // Rotación en el eje X
    val rotationY = rotation.x * PI / 180  // Rotación en el eje Y

    val points = mutableListOf<Offset>()
    val baseVertices = 30

    for (i in 0 until baseVertices) {
        val angle = 2 * PI * i / baseVertices
        val x = radius * cos(angle)
        val y = height / 2
        val z = radius * sin(angle)

        // Rotación en torno al eje X
        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ = y * sin(rotationX) + z * cos(rotationX)

        // Rotación en torno al eje Y
        val rotatedX = x * cos(rotationY) - rotatedZ * sin(rotationY)
        val rotatedZFinal = x * sin(rotationY) + rotatedZ * cos(rotationY)

        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    for (i in 0 until baseVertices) {
        val angle = 2 * PI * i / baseVertices
        val x = radius * cos(angle)
        val y = -height / 2
        val z = radius * sin(angle)

        // Rotación en torno al eje X
        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ = y * sin(rotationX) + z * cos(rotationX)

        // Rotación en torno al eje Y
        val rotatedX = x * cos(rotationY) - rotatedZ * sin(rotationY)
        val rotatedZFinal = x * sin(rotationY) + rotatedZ * cos(rotationY)

        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    return points
}
