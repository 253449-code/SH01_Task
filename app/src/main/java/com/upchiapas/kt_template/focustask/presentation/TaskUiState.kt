package com.upchiapas.kt_template.focustask.presentation

import com.upchiapas.kt_template.focustask.data.model.Category
import com.upchiapas.kt_template.focustask.data.model.Priority
import com.upchiapas.kt_template.focustask.data.model.Task

// Estado Inmutable de la Interfaz (UDF)
data class TaskUiState(
    // Datos de entrada del formulario
    val draftTitle: String = "",
    val draftCategory: Category = Category.ESTUDIO,
    val draftHours: Float = 1.0f,
    val draftPriority: Priority = Priority.MEDIA,

    // Lista de tareas a mostrar
    val tasks: List<Task> = emptyList(),

    // Filtro
    val showCompleted: Boolean = true,

    // Métricas calculadas
    val totalScore: Int = 0,
    val progressPercentage: Float = 0f
)