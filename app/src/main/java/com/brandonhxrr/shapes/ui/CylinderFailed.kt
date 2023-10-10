package com.brandonhxrr.shapes.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TridimensionalCylinder2() {
    var rotationState by remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    rotationState = Offset(
                        rotationState.x + pan.x,
                        rotationState.y + pan.y
                    )
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            val cylinderRadius = canvasWidth / 4
            val cylinderHeight = canvasWidth / 2

            val segments = 36
            val segmentAngle = 2 * PI / segments

            val rotatedPoints =
                calculateRotatedPoints(rotationState, cylinderRadius, centerX, centerY)

            repeat(segments) { i ->
                val angle = i * segmentAngle
                val x = rotatedPoints[0].x + cylinderRadius * cos(angle)
                val y = rotatedPoints[0].y + cylinderRadius * sin(angle)
                drawCircle(
                    color = Color.Blue,
                    radius = 4f,
                    center = Offset(x.toFloat(), y.toFloat())
                )
            }

            repeat(segments) { i ->
                val angle = i * segmentAngle
                val x = rotatedPoints[1].x + cylinderRadius * cos(angle)
                val y = rotatedPoints[1].y + cylinderRadius * sin(angle)
                drawCircle(
                    color = Color.Red,
                    radius = 4f,
                    center = Offset(x.toFloat(), y.toFloat())
                )
            }

            repeat(segments) { i ->
                val angle = i * segmentAngle
                val x1 = rotatedPoints[0].x + cylinderRadius * cos(angle)
                val y1 = rotatedPoints[0].y + cylinderRadius * sin(angle)
                val x2 = rotatedPoints[1].x + cylinderRadius * cos(angle)
                val y2 = rotatedPoints[1].y + cylinderRadius * sin(angle)
                drawLine(
                    color = Color.Green,
                    start = Offset(x1.toFloat(), y1.toFloat()),
                    end = Offset(x2.toFloat(), y2.toFloat()),
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
    for (i in 0 until 5) {
        val x = if (i and 2 == 0) size / 2 else -size / 2
        val y = if (i and 1 == 0) size / 2 else -size / 2
        val z = if (i and 4 == 0) size / 2 else -size / 2

        // Rotación en el eje X
        val rotatedY = y * cos(rotationX) - z * sin(rotationX)
        val rotatedZ = y * sin(rotationX) + z * cos(rotationX)

        // Rotación en el eje Y
        val rotatedX = x * cos(rotationY) + rotatedZ * sin(rotationY)
        val rotatedZFinal = -x * sin(rotationY) + rotatedZ * cos(rotationY)

        // Ajuste para cambiar la orientación de la cara frontal y la de abajo
        points.add(Offset((centerX + rotatedX).toFloat(), (centerY + rotatedY).toFloat()))
    }

    return points
}



