package com.example.lingocards.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lingocards.data.AppConstants
import com.example.lingocards.data.CardData
import com.example.lingocards.data.TopicData
import com.example.lingocards.ui.components.InfoCards
import com.example.lingocards.ui.components.TopicButtons
import com.example.lingocards.ui.theme.LingoCardsTheme
import com.example.lingocards.ui.components.AddCardDialog
import com.example.lingocards.ui.components.AddTopicDialog

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    ) {
    var showAddTopicDialog by remember { mutableStateOf(false) }
    var showAddCardDialog by remember { mutableStateOf(false) }
    var newTopicName by remember { mutableStateOf("") }
    var newEnglish by remember { mutableStateOf("") }
    var newFinnish by remember { mutableStateOf("") }

    // Function to handle "Add Topic" button click
    fun onAddTopicRequest() {
        showAddTopicDialog = true
    }

    // Function to handle "Add Card" button click
    fun onAddCardRequest(topic: String) {
        showAddCardDialog = true
    }

    val topics = TopicData.topics
    var selectedTopic by remember { mutableStateOf(topics.firstOrNull() ?: "") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Topic Buttons
        if (topics.isEmpty()) {
            // Fallback for No Topics
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = AppConstants.NO_TOPICS_AVAILABLE,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            TopicButtons(
                topics = topics,
                selectedTopic = selectedTopic,
                onTopicSelected = { topic ->
                    selectedTopic = topic
                },
                onAddTopicRequest = ::onAddTopicRequest
            )

            // Info Cards
            val cards = TopicData.topicInfoCards[selectedTopic] ?: emptyList()

            if (cards.isEmpty()) {
                // Fallback for No Cards
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = AppConstants.NO_CARDS_AVAILABLE,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = AppConstants.NO_INFORMATION_AVAILABLE,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                InfoCards(
                    cards = cards.map { card ->
                        CardData(
                            englishSentence = card.englishSentence,
                            finnishTranslation = card.finnishTranslation.ifEmpty { AppConstants.TRANSLATION_NOT_AVAILABLE }
                        )
                    },
                    onAddCardRequest = ::onAddCardRequest,
                    modifier = Modifier.fillMaxSize(),
                    selectedTopic = selectedTopic
                )
            }
        }
    }

    // Add Topic Button Logic
    if (showAddTopicDialog) {
        AddTopicDialog(
            newTopicName = newTopicName,
            onTopicNameChanged = { newTopicName = it },
            onSave = {
                if (newTopicName.isNotBlank()) {
                    TopicData.topics.add(newTopicName)
                    showAddTopicDialog = false
                }
            },
            onDismiss = { showAddTopicDialog = false }
        )
    }

    // Add Card Button Logic
    if (showAddCardDialog) {
        AddCardDialog(
            selectedTopic = selectedTopic,
            newEnglish = newEnglish,
            newFinnish = newFinnish,
            onEnglishChanged = { newEnglish = it },
            onFinnishChanged = { newFinnish = it },
            onSave = {
                if (newEnglish.isNotBlank() && newFinnish.isNotBlank()) {
                    TopicData.topicInfoCards[selectedTopic]?.add(
                        CardData(newEnglish, newFinnish)
                    )
                    showAddCardDialog = false
                }
            },
            onDismiss = { showAddCardDialog = false },
            onTranslate = {
                // Step 6: Fetch translation via API here
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LingoCardsTheme {
        HomeScreen()
    }
}
