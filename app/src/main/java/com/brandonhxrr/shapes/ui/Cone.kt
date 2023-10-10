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
fun TridimensionalCone() {
    var rotationState by remember { mutableStateOf(Offset(0f, 0f)) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(yellow)
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    rotationState = Offset(
                        rotationState.x + pan.x / 4f,
                        rotationState.y + pan.y / 4f
                    )
                }
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2

        val coneSize = canvasWidth / 3

        val basePointsCount = 24
        val angleIncrement = 2 * PI / basePointsCount
        val basePoints3D = mutableListOf<Triple<Float, Float, Float>>()

        for (i in 0 until basePointsCount) {
            val angle = i * angleIncrement
            val x = coneSize * cos(angle)
            val y = coneSize * sin(angle)
            basePoints3D.add(Triple(x.toFloat(), y.toFloat(), 0f))
        }

        val topPoint3D = Triple(0f, 0f, -coneSize * 2)

        val rotatedBasePoints = basePoints3D.map { point ->
            val (x, y, z) = point
            val rotatedX = x * cos(rotationState.y) - z * sin(rotationState.y)
            val rotatedZ = x * sin(rotationState.y) + z * cos(rotationState.y)
            val rotatedY = y * cos(rotationState.x) - rotatedZ * sin(rotationState.x)
            val rotatedZFinal = y * sin(rotationState.x) + rotatedZ * cos(rotationState.x)
            Triple(rotatedX, rotatedY, rotatedZFinal)
        }

        val perspective = 2 * canvasWidth

        val basePoints2D = rotatedBasePoints.map { point ->
            val (x, y, z) = point
            val scaleFactor = perspective / (perspective + z)
            Offset(centerX + x * scaleFactor, centerY + y * scaleFactor)
        }

        for (i in 0 until basePointsCount) {
            drawLine(
                color = Color.Black,
                start = basePoints2D[i],
                end = basePoints2D[(i + 1) % basePointsCount],
                strokeWidth = 2f
            )
        }

        val topPoint2D = Offset(centerX, centerY - coneSize * 2)
        for (i in 0 until basePointsCount) {
            drawLine(
                color = Color.Black,
                start = basePoints2D[i],
                end = topPoint2D,
                strokeWidth = 2f
            )
        }
    }
}
