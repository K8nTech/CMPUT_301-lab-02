package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.listycity.ui.theme.ListyCityTheme

// imported new packages
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ElevatedButton
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        onDeleteCity = { cityRepository.deleteCity(it) },
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun ListyCityHeader(){ // header for listy city app, displays title
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Magenta)
            .padding(28.dp)
    ) {
        Text(
            text = "Listy City",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold

        )
    }
}
@Composable
fun CityRow(city: String, cityClicked: (String) -> Unit, selectedCity: Boolean) { // implemented two more variables for click check
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (selectedCity)
                    Color.LightGray
                else
                    (Color.Transparent)
            )
            .clickable {
                cityClicked(city)
            }
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
        Text(
            text = city,
        )
    }
}
@Composable
fun CityListScreen(
    cities: List<String>,
    modifier: Modifier = Modifier,
    onAddCity: (String) -> Unit,
    onDeleteCity: (String) -> Unit
) {
    var newCityName by remember { mutableStateOf(value = "") }
    var cityClicked by remember { mutableStateOf<String?>(null) } // not selected yet

    Column(modifier = modifier.fillMaxSize()) {
        ListyCityHeader()

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cities) { city ->
                CityRow(
                    city = city,
                    selectedCity = city == cityClicked,
                    cityClicked = { cityClicked = it })
            }
        }
        Row(modifier = Modifier.padding(all = 10.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("Enter city name") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Magenta,
                    unfocusedBorderColor = Color.Magenta,
                    cursorColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                ElevatedButton(
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            onAddCity(newCityName)
                            newCityName = ""
                        }
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.Magenta,
                        contentColor = Color.White
                    )
                ) {
                    Text("Add City")
                }

                Spacer(modifier = Modifier.height(4.dp))

                ElevatedButton(
                    onClick = {
                        if (cityClicked != null) {
                            onDeleteCity(cityClicked!!)
                            cityClicked = null
                        }
                    },
                    enabled =  cityClicked != null, // verify if city has been clicked
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.Magenta,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Delete City")
                }
            }
        }
    }
}