package com.upchiapas.kt_template.focustask.presentation

import androidx.lifecycle.ViewModel
import com.upchiapas.kt_template.focustask.data.local.seedTasks
import com.upchiapas.kt_template.focustask.data.model.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(tasks = seedTasks)
        }
        recalculateProgress()
    }

    fun toggleTaskCompletion(taskId: String) {
        _uiState.update { currentState ->
            val updatedTasks = currentState.tasks.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
            }
            currentState.copy(tasks = updatedTasks)
        }
        recalculateProgress()
    }

    private fun recalculateProgress() {
        val tasks = _uiState.value.tasks
        val totalPossible = tasks.sumOf { it.priority.multiplier }
        val currentScore = tasks.filter { it.isCompleted }.sumOf { it.priority.multiplier }

        val progress = if (totalPossible > 0) currentScore.toFloat() / totalPossible else 0f

        _uiState.update {
            it.copy(totalScore = currentScore, progressPercentage = progress)
        }
    }

    // Funciones del formulario
    fun updateDraftTitle(title: String) { _uiState.update { it.copy(draftTitle = title) } }

    fun updateDraftHours(hours: Float) { _uiState.update { it.copy(draftHours = hours) } }

    fun updateDraftPriority(priority: Priority) { _uiState.update { it.copy(draftPriority = priority) } }

    fun saveTask() {
        // Simula guardar limpiando el formulario por ahora
        _uiState.update {
            it.copy(
                draftTitle = "",
                draftHours = 1.0f,
                draftPriority = Priority.MEDIA
            )
        }
    }
}