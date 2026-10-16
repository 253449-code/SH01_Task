package com.upchiapas.kt_template.focustask.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.kt_template.focustask.presentation.components.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel() // Inyectamos el ViewModel
) {
    // 1. Recolectamos el estado de forma segura (UDF)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FocusTask") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // 2. Lista perezosa (LazyColumn) para mostrar los datos
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Iteramos sobre la lista de tareas del estado inmutable
            items(uiState.tasks) { task ->
                TaskCard(
                    task = task,
                    onToggleCompletion = { taskId ->
                        // 3. Enviamos el evento al ViewModel
                        viewModel.toggleTaskCompletion(taskId)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}