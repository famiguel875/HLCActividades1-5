package com.example.actividades1_5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.focus.onFocusChanged

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Preview(showBackground = true)
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if (showLoading) "Cancelar" else "Cargar perfil")
        }
    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/
@Preview(showBackground = true)
@Composable
fun Actividad2() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var showSpacer by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
            showSpacer = true
        } else {
            showSpacer = false
        }

        // Agregar separación solo si el progreso es visible
        if (showSpacer) {
            Spacer(modifier = Modifier.height(30.dp))
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if (showLoading) "Cancelar" else "Cargar perfil")
        }
    }
}

/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/
@Preview(showBackground = true)
@Composable
fun Actividad3() {
    var progress by rememberSaveable { mutableFloatStateOf(0f) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { if (progress < 1f) progress += 0.1f },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(text = "Incrementar")
            }
            Button(
                onClick = { if (progress > 0f) progress -= 0.1f }) {
                Text(text = "Reducir")
            }
        }
    }
}

/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/
@Preview(showBackground = true)
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = myVal,
            onValueChange = { input ->
                if (input.count { it == '.' } <= 1) {
                    myVal = input.replace(',', '.')
                }
            },
            label = { Text(text = "Importe") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@Preview(showBackground = true)
@Composable
fun ParentActividad5() {
    // Estado elevado para manejar el valor del campo de texto
    var myVal by rememberSaveable { mutableStateOf("") }

    Actividad5Content(
        value = myVal,
        onValueChange = { input ->
            // Reemplaza la coma por un punto
            val sanitizedInput = input.replace(',', '.')

            // Validar si es un número decimal válido
            var validInput = true
            var decimalCount = 0

            for (char in sanitizedInput) {
                if (char == '.') {
                    decimalCount++
                    if (decimalCount > 1) validInput = false
                } else if (!char.isDigit() && char != '.') {
                    validInput = false
                }
            }

            // Actualizar el valor solo si es válido
            if (validInput) {
                myVal = sanitizedInput
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Actividad5Content(value: String, onValueChange: (String) -> Unit) {
    // Estado local para manejar el foco del campo de texto
    var hasFocus by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Importe") },
        modifier = Modifier
            .padding(15.dp)
            .onFocusChanged { focusState ->
                hasFocus = focusState.isFocused
            },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (hasFocus) Color.Green else Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}