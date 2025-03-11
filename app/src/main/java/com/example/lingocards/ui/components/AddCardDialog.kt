package com.example.lingocards.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddCardDialog(
    selectedTopic: String,
    newEnglish: String,
    newFinnish: String,
    onEnglishChanged: (String) -> Unit,
    onFinnishChanged: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    onTranslate: () -> Unit
) {
    var isTranslating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Mock API function to simulate translation
    suspend fun fetchTranslation(english: String): String {
        delay(1000) // Simulate network delay
        return when (english.lowercase()) {
            "hello" -> "Hei"
            "thank you" -> "Kiitos"
            "how are you?" -> "Mitä kuuluu?"
            else -> "Käännös ei saatavilla" // "Translation not available"
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Card for $selectedTopic") },
        text = {
            Column {
                TextField(
                    value = newEnglish,
                    onValueChange = onEnglishChanged,
                    label = { Text("English Sentence") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = newFinnish,
                    onValueChange = onFinnishChanged,
                    label = { Text("Finnish Translation") },
                    enabled = false // Disable manual input; translation is fetched
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        isTranslating = true
                        coroutineScope.launch {
                            val translation = fetchTranslation(newEnglish)
                            onFinnishChanged(translation)
                            isTranslating = false
                        }
                    },
                    enabled = newEnglish.isNotBlank() && !isTranslating
                ) {
                    Text(if (isTranslating) "Translating..." else "Translate")
                }
            }
        },
        confirmButton = {
            Button(onClick = onSave) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
