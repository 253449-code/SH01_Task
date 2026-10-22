package com.upchiapas.kt_template.focustask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.kt_template.focustask.data.model.Task
import com.upchiapas.kt_template.focustask.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.tasksFlow.collectLatest { tasksList ->
                _uiState.update { it.copy(tasks = tasksList) }
            }
        }
        viewModelScope.launch {
            repository.filterStateFlow.collectLatest { filter ->
                _uiState.update { it.copy(showCompleted = filter) }
            }
        }
    }

    fun updateTitle(newTitle: String) { _uiState.update { it.copy(currentTitle = newTitle) } }
    fun updateDescription(newDesc: String) { _uiState.update { it.copy(currentDescription = newDesc) } }
    fun updateCategory(newCat: String) { _uiState.update { it.copy(currentCategory = newCat) } }
    fun updateTime(newTime: Double) { _uiState.update { it.copy(currentTime = newTime) } }
    fun updatePriority(newPriority: String) { _uiState.update { it.copy(currentPriority = newPriority) } }

    fun addTask() {
        val state = _uiState.value
        if (state.currentTitle.isNotBlank()) {
            val newTask = Task(
                title = state.currentTitle,
                description = state.currentDescription,
                category = state.currentCategory,
                estimatedTime = state.currentTime,
                priority = state.currentPriority
            )
            viewModelScope.launch { repository.saveTask(newTask) }
            _uiState.update { it.copy(currentTitle = "", currentDescription = "", currentTime = 2.0) }
        }
    }

    fun toggleTaskCompletion(task: Task) {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        viewModelScope.launch { repository.updateTask(updatedTask) }
    }

    fun toggleFilterState(show: Boolean) {
        viewModelScope.launch { repository.saveFilterState(show) }
    }

    fun resetData() {
        viewModelScope.launch { repository.resetToSeedData() }
    }
}