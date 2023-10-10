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
fun Esfera() {
    var rotationState by remember { mutableStateOf(Offset(0f, 0f)) }

    val radius = 200f
    val segments = 20

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

        val puntosEsfera = calcularCoordenadasEsfera(radius, segments)

        val perspective = 2 * canvasWidth // Ajuste de perspectiva
        val puntos2D = puntosEsfera.map { (x, y, z) ->

            val rotatedX = x * cos(rotationState.y) - z * sin(rotationState.y)
            val rotatedZ = x * sin(rotationState.y) + z * cos(rotationState.y)
            val rotatedY = y * cos(rotationState.x) - rotatedZ * sin(rotationState.x)
            val rotatedZFinal = y * sin(rotationState.x) + rotatedZ * cos(rotationState.x)

            val scaleFactor = perspective / (perspective + rotatedZFinal)
            Offset(centerX + rotatedX * scaleFactor, centerY - rotatedY * scaleFactor)
        }

        for (i in 0 until segments - 1) {
            for (j in 0 until segments * 2) {
                val p1 = puntos2D[i * segments * 2 + j]
                val p2 = puntos2D[i * segments * 2 + (j + 1) % (segments * 2)]
                val p3 = puntos2D[(i + 1) * segments * 2 + j]
                val p4 = puntos2D[(i + 1) * segments * 2 + (j + 1) % (segments * 2)]

                drawLine(color = Color.Black, start = p1, end = p2, strokeWidth = 2f)
                drawLine(color = Color.Black, start = p1, end = p3, strokeWidth = 2f)
                if (i < segments - 1) {
                    drawLine(color = Color.Black, start = p3, end = p4, strokeWidth = 2f)
                    drawLine(color = Color.Black, start = p2, end = p4, strokeWidth = 2f)
                }
            }
        }
    }
}



fun calcularCoordenadasEsfera(radius: Float, segments: Int): List<Triple<Float, Float, Float>> {
    val puntos = mutableListOf<Triple<Float, Float, Float>>()

    for (i in 0 until segments) {
        val phi = (i * PI / segments).toFloat()
        for (j in 0 until segments * 2) {
            val theta = (j * PI / segments).toFloat()

            val x = radius * sin(phi) * cos(theta)
            val y = radius * sin(phi) * sin(theta)
            val z = radius * cos(phi)

            puntos.add(Triple(x, y, z))
        }
    }

    return puntos
}
