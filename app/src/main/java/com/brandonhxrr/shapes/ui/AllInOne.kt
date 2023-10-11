package com.brandonhxrr.shapes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brandonhxrr.shapes.ui.theme.yellow

@Composable
fun AllInOne(rotationState: Offset) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(yellow)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .border(1.dp, Color.Red)
            ) {
                TridimensionalCube(rotationState)
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .border(1.dp, Color.Blue)
            ) {
                TridimensionalCone(rotationState)
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .border(1.dp, Color.Magenta)
            ) {
                TridimensionalCylinder(rotationState)
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .border(1.dp, Color.Green)
            ) {
                Esfera(rotationState)
            }
        }
    }

}