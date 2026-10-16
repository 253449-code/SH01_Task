package com.upchiapas.kt_template.focustask.data.local

import com.upchiapas.kt_template.focustask.data.model.Category
import com.upchiapas.kt_template.focustask.data.model.Priority
import com.upchiapas.kt_template.focustask.data.model.Task

// 4 Tareas iniciales precargadas (Requisito de la rúbrica)
val seedTasks = listOf(
    Task(title = "Revisar arquitectura MVVM", category = Category.ESTUDIO, estimatedHours = 2.0f, priority = Priority.ALTA, isCompleted = true),
    Task(title = "Diseñar UI en Compose", category = Category.TRABAJO, estimatedHours = 1.5f, priority = Priority.ALTA, isCompleted = true),
    Task(title = "Crear Repository DataStore", category = Category.TRABAJO, estimatedHours = 3.0f, priority = Priority.ALTA, isCompleted = false),
    Task(title = "Pruebas unitarias", category = Category.ESTUDIO, estimatedHours = 1.0f, priority = Priority.MEDIA, isCompleted = false)
)