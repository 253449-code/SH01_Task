package com.upchiapas.kt_template.focustask.presentation

import androidx.lifecycle.ViewModel
import com.upchiapas.kt_template.focustask.data.local.seedTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    // 1. Backing Property: Privado para modificar, publico de solo lectura para la UI
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        // 2. Al iniciar, cargamos los datos semilla (nuestras 4 tareas base)
        _uiState.update { currentState ->
            currentState.copy(tasks = seedTasks)
        }
    }

    // 3. Evento: El usuario toca el switch de una tarea
    fun toggleTaskCompletion(taskId: String) {
        _uiState.update { currentState ->
            val updatedTasks = currentState.tasks.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
            }
            currentState.copy(tasks = updatedTasks)
        }
    }
}