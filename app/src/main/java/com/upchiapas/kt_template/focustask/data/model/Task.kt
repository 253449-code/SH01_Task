package com.upchiapas.kt_template.focustask.data.model

enum class Priority(val multiplier: Int) {
    ALTA(3), MEDIA(2), BAJA(1)
}

enum class Category {
    ESTUDIO, TRABAJO, PERSONAL
}

// Modelo de datos de la tarea (Requisito de datos semilla/persistencia)
data class Task(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val category: Category,
    val estimatedHours: Float,
    val priority: Priority,
    val isCompleted: Boolean = false
)