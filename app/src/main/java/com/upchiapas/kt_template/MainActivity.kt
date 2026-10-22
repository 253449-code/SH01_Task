package com.upchiapas.kt_template

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.upchiapas.kt_template.focustask.data.repository.TaskRepositoryImpl
import com.upchiapas.kt_template.focustask.presentation.TaskScreen
import com.upchiapas.kt_template.focustask.presentation.TaskViewModel
// Ajusta si tu tema tiene otro nombre
import com.upchiapas.kt_template.ui.theme.Kt_templateTheme

private val Context.dataStore by preferencesDataStore(name = "focustask_prefs")

class TaskViewModelFactory(private val repository: TaskRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepositoryImpl(applicationContext.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Kt_templateTheme {
                TaskScreen(viewModel = taskViewModel)
            }
        }
    }
}