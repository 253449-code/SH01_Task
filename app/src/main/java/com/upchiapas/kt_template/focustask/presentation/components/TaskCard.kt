package com.upchiapas.kt_template.focustask.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TaskCard(task: com.upchiapas.kt_template.focustask.data.model.Task, onToggle: () -> Unit) {
    val borderColor = if (task.isCompleted) Color(0xFF4CAF50) else Color.LightGray
    val statusText = if (task.isCompleted) "Completada (+${task.points} pts)" else "Pendiente (${task.points} pts posibles)"
    val textColor = if (task.isCompleted) Color(0xFF4CAF50) else Color(0xFFE65100)

    Card(
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.isCompleted, onCheckedChange = { onToggle() })
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(text = task.title, color = if(task.isCompleted) Color.Gray else Color.Black)
                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(text = "${task.category} | ${task.estimatedTime} hrs | Prioridad ${task.priority}", style = MaterialTheme.typography.bodySmall)
                Text(text = statusText, color = textColor, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}