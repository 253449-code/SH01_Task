package com.upchiapas.kt_template.focustask.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.upchiapas.kt_template.focustask.presentation.components.TaskCard
import com.upchiapas.kt_template.focustask.presentation.components.TaskInputForm

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val completedCount = uiState.tasks.count { it.isCompleted }
    val totalCount = uiState.tasks.size
    val totalPoints = uiState.tasks.filter { it.isCompleted }.sumOf { it.points }
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    val filteredTasks = if (uiState.showCompleted) uiState.tasks else uiState.tasks.filter { !it.isCompleted }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            TaskInputForm(
                title = uiState.currentTitle, onTitleChange = viewModel::updateTitle,
                description = uiState.currentDescription, onDescriptionChange = viewModel::updateDescription,
                category = uiState.currentCategory, onCategoryChange = viewModel::updateCategory,
                time = uiState.currentTime, onTimeChange = viewModel::updateTime,
                priority = uiState.currentPriority, onPriorityChange = viewModel::updatePriority,
                onAddClick = viewModel::addTask
            )
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("[02] METRICAS Y TAREAS", fontWeight = FontWeight.Bold)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("FILTRAR COMPLETADAS:")
                            Switch(checked = uiState.showCompleted, onCheckedChange = viewModel::toggleFilterState)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("PROGRESO: ${(progress * 100).toInt()}% ($completedCount/$totalCount Completadas)")
                        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                        Text("Puntaje Total: $totalPoints pts acumulados", color = Color(0xFF4CAF50))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("TAREAS PERSISTENTES (DATASTORE):")
            }
        }

        items(filteredTasks) { task ->
            TaskCard(task = task, onToggle = { viewModel.toggleTaskCompletion(task) })
        }

        item {
            OutlinedButton(
                onClick = viewModel::resetData,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("REINICIAR TAREAS BASE")
            }
        }
    }
}