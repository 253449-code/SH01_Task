package com.upchiapas.kt_template.focustask.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.upchiapas.kt_template.focustask.data.model.Task

// Componente 100% Stateless: No guarda estado, solo lo recibe de arriba.
@Composable
fun TaskCard(
    task: Task,
    onToggleCompletion: (String) -> Unit, // Función lambda para enviar el evento hacia arriba
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${task.category.name} | ${task.estimatedHours}h | Prioridad: ${task.priority.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            // Switch de completado
            Switch(
                checked = task.isCompleted,
                onCheckedChange = { onToggleCompletion(task.id) }
            )
        }
    }
}