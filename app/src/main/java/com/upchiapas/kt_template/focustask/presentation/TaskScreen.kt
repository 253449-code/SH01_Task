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
import com.upchiapas.kt_template.focustask.presentation.components.ProgressSection
import com.upchiapas.kt_template.focustask.presentation.components.TaskCard
import com.upchiapas.kt_template.focustask.presentation.components.TaskInputForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = viewModel()
) {
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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                TaskInputForm(
                    draftTitle = uiState.draftTitle,
                    onTitleChange = { viewModel.updateDraftTitle(it) },
                    draftHours = uiState.draftHours,
                    onHoursChange = { viewModel.updateDraftHours(it) },
                    draftPriority = uiState.draftPriority,
                    onPrioritySelect = { viewModel.updateDraftPriority(it) },
                    onSaveTask = { viewModel.saveTask() }
                )
                Spacer(modifier = Modifier.height(16.dp))

                ProgressSection(
                    progressPercentage = uiState.progressPercentage,
                    totalScore = uiState.totalScore
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(uiState.tasks) { task ->
                TaskCard(
                    task = task,
                    onToggleCompletion = { taskId ->
                        viewModel.toggleTaskCompletion(taskId)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}