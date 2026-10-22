package com.upchiapas.kt_template.focustask.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressSection(completedTasks: Int, totalTasks: Int) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nivel de Productividad: $completedTasks / $totalTasks pts")
            Spacer(modifier = Modifier.height(8.dp))
            val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks.toFloat() else 0f
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}