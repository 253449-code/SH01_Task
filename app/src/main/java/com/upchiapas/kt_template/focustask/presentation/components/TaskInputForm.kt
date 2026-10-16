package com.upchiapas.kt_template.focustask.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.upchiapas.kt_template.focustask.data.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputForm(
    draftTitle: String,
    onTitleChange: (String) -> Unit,
    draftHours: Float,
    onHoursChange: (Float) -> Unit,
    draftPriority: Priority,
    onPrioritySelect: (Priority) -> Unit,
    onSaveTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Crear Nueva Tarea", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = draftTitle,
                onValueChange = onTitleChange,
                label = { Text("¿Que necesitas hacer?") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(text = "Tiempo estimado: ${String.format("%.1f", draftHours)} horas", style = MaterialTheme.typography.bodyMedium)
            Slider(
                value = draftHours,
                onValueChange = onHoursChange,
                valueRange = 0.5f..4.0f,
                steps = 6
            )

            Text(text = "Prioridad:", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Priority.values().forEach { priority ->
                    FilterChip(
                        selected = draftPriority == priority,
                        onClick = { onPrioritySelect(priority) },
                        label = { Text(priority.name) }
                    )
                }
            }

            Button(
                onClick = onSaveTask,
                modifier = Modifier.align(Alignment.End),
                enabled = draftTitle.isNotBlank()
            ) {
                Text("Agregar")
            }
        }
    }
}