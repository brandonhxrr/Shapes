package com.brandonhxrr.shapes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
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
fun TridimensionalCone(rotationState: Offset) {
    var currentRotationState by remember { mutableStateOf(rotationState) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
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
        val canvasWidth = size.width
        val canvasHeight = size.height

        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2

        val coneRadius = canvasWidth / 4
        val coneHeight = canvasHeight / 3
        val baseVertices = 30

        val rotatedPoints =
            calculateRotatedPoints(currentRotationState, coneRadius, coneHeight, centerX, centerY)

        for (i in 0 until baseVertices) {
            drawLine(
                color = Color.Black,
                start = rotatedPoints[i],
                end = rotatedPoints[(i + 1) % baseVertices],
                strokeWidth = 2f
            )
        }

        val topPoint = calculateRotatedTopPoint(currentRotationState, coneRadius, coneHeight, centerX, centerY)
        for (i in 0 until baseVertices) {
            drawLine(
                color = Color.Black,
                start = rotatedPoints[i],
                end = topPoint,
                strokeWidth = 2f
            )
        }
    }
}

private fun calculateRotatedPoints(
    rotation: Offset, radius: Float, height: Float, centerX: Float, centerY: Float
): List<Offset> {
    val rotationX = rotation.x * PI / 180  // Rotación en torno al eje X
    val rotationY = rotation.y * PI / 180  // Rotación en torno al eje Y

    val points = mutableListOf<Offset>()
    val baseVertices = 30

    for (i in 0 until baseVertices) {
        val angle = 2 * PI * i / baseVertices
        val x = radius * cos(angle)
        val y = -height / 2
        val z = radius * sin(angle)

        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ = y * sin(rotationX) + z * cos(rotationX)

        val rotatedX = x * cos(rotationY) - rotatedZ * sin(rotationY)
        val rotatedZFinal = x * sin(rotationY) + rotatedZ * cos(rotationY)

        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    return points
}

private fun calculateRotatedTopPoint(rotation: Offset, radius: Float, height: Float, centerX: Float, centerY: Float): Offset {
    val rotationX = rotation.x * PI / 180
    val rotationY = rotation.y * PI / 180

    val x = 0f
    val y = height / 2
    val z = 0f

    val rotatedX = x * cos(rotationY) - z * sin(rotationY)
    val rotatedZ = x * sin(rotationY) + z * cos(rotationY)

    val rotatedYFinal = y * cos(rotationX) - rotatedZ * sin(rotationX)
    val rotatedZFinal = y * sin(rotationX) + rotatedZ * cos(rotationX)

    return Offset((centerX + rotatedX).toFloat(), (centerY + rotatedYFinal).toFloat())
}