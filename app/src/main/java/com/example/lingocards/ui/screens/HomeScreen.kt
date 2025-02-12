package com.example.lingocards.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
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
                }
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
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LingoCardsTheme {
        HomeScreen()
    }
}
