package com.upchiapas.kt_template.focustask.presentation

import com.upchiapas.kt_template.focustask.data.model.Task

data class TaskUiState(
    val currentTitle: String = "",
    val currentDescription: String = "",
    val currentCategory: String = "Estudio",
    val currentTime: Double = 2.0,
    val currentPriority: String = "Media",
    val tasks: List<Task> = emptyList(),
    val showCompleted: Boolean = true
) {
    val currentTaskPoints: Int get() = Task(
        title = "", category = currentCategory, estimatedTime = currentTime, priority = currentPriority
    ).points
}