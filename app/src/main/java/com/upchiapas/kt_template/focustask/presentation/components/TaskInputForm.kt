package com.upchiapas.kt_template.focustask.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TaskInputForm(
    title: String, onTitleChange: (String) -> Unit,
    description: String, onDescriptionChange: (String) -> Unit,
    category: String, onCategoryChange: (String) -> Unit,
    time: Double, onTimeChange: (Double) -> Unit,
    priority: String, onPriorityChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    // Configuración para forzar el texto negro en las entradas
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        cursorColor = Color.Black
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("[01] CREAR NUEVA TAREA", fontWeight = FontWeight.Bold)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text("TITULO DE LA TAREA:")
        OutlinedTextField(
            value = title, onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(), singleLine = true,
            colors = textFieldColors // Aplicamos color negro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("DESCRIPCION:")
        OutlinedTextField(
            value = description, onValueChange = onDescriptionChange,
            modifier = Modifier.fillMaxWidth(), minLines = 2,
            colors = textFieldColors // Aplicamos color negro
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("CATEGORIA:")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Estudio", "Trabajo", "Personal").forEach { cat ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(selected = category == cat, onClick = { onCategoryChange(cat) })
                    Text(cat)
                }
            }
        }

        // ... (resto del código superior se mantiene igual)
        Spacer(modifier = Modifier.height(16.dp))
        Text("TIEMPO ESTIMADO: ${String.format("%.1f", time)} hrs")

        // --- SLIDER ACTUALIZADO: Hasta 10 horas con incrementos de 0.5 ---
        Slider(
            value = time.toFloat(),
            onValueChange = { onTimeChange(it.toDouble()) },
            valueRange = 0.5f..10.0f, // Rango ampliado
            steps = 18 // Número de paradas intermedias
        )
        // ... (resto del código inferior se mantiene igual)

        Spacer(modifier = Modifier.height(8.dp))
        Text("PRIORIDAD:")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Alta", "Media", "Baja").forEach { prio ->
                FilterChip(
                    selected = priority == prio,
                    onClick = { onPriorityChange(prio) },
                    label = { Text(prio) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onAddClick, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("+ GUARDAR TAREA", color = Color.White)
        }
    }
}