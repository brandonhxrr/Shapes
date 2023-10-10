package com.brandonhxrr.shapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.FiberSmartRecord
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandonhxrr.shapes.ui.Esfera
import com.brandonhxrr.shapes.ui.Screens
import com.brandonhxrr.shapes.ui.TridimensionalCone
import com.brandonhxrr.shapes.ui.TridimensionalCube
import com.brandonhxrr.shapes.ui.TridimensionalCylinder
import com.brandonhxrr.shapes.ui.theme.ShapesTheme
import com.brandonhxrr.shapes.ui.theme.darkNavy
import com.brandonhxrr.shapes.ui.theme.lightPurpleBlue
import com.brandonhxrr.shapes.ui.theme.yellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShapesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Start()
                }
            }
        }
    }
}

@Composable
fun Start() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Menu.name) {
        composable(Screens.Menu.name) {
            MainMenu(navController = navController)
        }
        composable(Screens.Cube.name) {
            TridimensionalCube()
        }
        composable(Screens.Cylinder.name) {
            TridimensionalCylinder()
        }
        composable(Screens.Cone.name) {
            TridimensionalCone()
        }
        composable(Screens.Sphere.name) {
            Esfera()
        }
    }
}

@Composable
fun MainMenu(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(yellow)
    ) {
        Row (
            modifier = Modifier
                .padding(30.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Icon(painter = painterResource(id = R.drawable.shapes), contentDescription = "Icon")
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Shapes",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Screens.Cube.name) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = darkNavy, contentColor = Color.White)
        ) {
            Text(text = "Cubo", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Screens.Cylinder.name) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = darkNavy, contentColor = Color.White)
        ) {
            Text(text = "Cilindro", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Screens.Cone.name) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = darkNavy, contentColor = Color.White)
        ) {
            Text(text = "Cono", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Screens.Sphere.name) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = darkNavy, contentColor = Color.White)
        ) {
            Text(text = "Esfera", fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShapesTheme {
        TridimensionalCube()
    }
}