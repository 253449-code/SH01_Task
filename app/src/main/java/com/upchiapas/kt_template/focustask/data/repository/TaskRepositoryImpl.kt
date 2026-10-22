package com.upchiapas.kt_template.focustask.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.upchiapas.kt_template.focustask.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

class TaskRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : TaskRepository {

    private val TASKS_KEY = stringPreferencesKey("tasks_data")
    private val FILTER_KEY = booleanPreferencesKey("filter_completed")

    override val tasksFlow: Flow<List<Task>> = dataStore.data.map { preferences ->
        val jsonString = preferences[TASKS_KEY] ?: "[]"
        parseJsonToTasks(jsonString)
    }

    override val filterStateFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[FILTER_KEY] ?: true
    }

    override suspend fun saveTask(task: Task) {
        dataStore.edit { prefs ->
            val currentJson = prefs[TASKS_KEY] ?: "[]"
            val currentList = parseJsonToTasks(currentJson).toMutableList()
            currentList.add(task)
            prefs[TASKS_KEY] = parseTasksToJson(currentList)
        }
    }

    override suspend fun updateTask(task: Task) {
        dataStore.edit { prefs ->
            val currentJson = prefs[TASKS_KEY] ?: "[]"
            val currentList = parseJsonToTasks(currentJson).toMutableList()
            val index = currentList.indexOfFirst { it.id == task.id }
            if (index != -1) {
                currentList[index] = task
                prefs[TASKS_KEY] = parseTasksToJson(currentList)
            }
        }
    }

    override suspend fun saveFilterState(showCompleted: Boolean) {
        dataStore.edit { prefs -> prefs[FILTER_KEY] = showCompleted }
    }

    override suspend fun resetToSeedData() {
        val seedTasks = listOf(
            Task(title = "Revisar arquitectura", description = "Leer documentación de MVVM", category = "Estudio", estimatedTime = 2.0, priority = "Alta"),
            Task(title = "Diseñar UI", description = "Crear pantallas en Compose", category = "Trabajo", estimatedTime = 1.5, priority = "Alta")
        )
        dataStore.edit { prefs -> prefs[TASKS_KEY] = parseTasksToJson(seedTasks) }
    }

    private fun parseTasksToJson(tasks: List<Task>): String {
        val jsonArray = JSONArray()
        tasks.forEach { task ->
            val obj = JSONObject().apply {
                put("id", task.id)
                put("title", task.title)
                put("description", task.description)
                put("category", task.category)
                put("estimatedTime", task.estimatedTime)
                put("priority", task.priority)
                put("isCompleted", task.isCompleted)
            }
            jsonArray.put(obj)
        }
        return jsonArray.toString()
    }

    private fun parseJsonToTasks(jsonString: String): List<Task> {
        val list = mutableListOf<Task>()
        try {
            val array = JSONArray(jsonString)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    Task(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        description = obj.optString("description", ""),
                        category = obj.getString("category"),
                        estimatedTime = obj.getDouble("estimatedTime"),
                        priority = obj.getString("priority"),
                        isCompleted = obj.getBoolean("isCompleted")
                    )
                )
            }
        } catch (e: Exception) { e.printStackTrace() }
        return list
    }
}