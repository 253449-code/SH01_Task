package com.upchiapas.kt_template.focustask.data.repository

import com.upchiapas.kt_template.focustask.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val tasksFlow: Flow<List<Task>>
    val filterStateFlow: Flow<Boolean>

    suspend fun saveTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun saveFilterState(showCompleted: Boolean)
    suspend fun resetToSeedData()
}