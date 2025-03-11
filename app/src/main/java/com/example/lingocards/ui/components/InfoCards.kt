package com.example.lingocards.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingocards.data.CardData
import com.example.lingocards.ui.theme.LingoCardsTheme
import kotlin.reflect.KFunction1

@Composable
fun InfoCards(
    cards: List<CardData>,
    selectedTopic: String,
    onAddCardRequest: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.8f // 80% of screen width

    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items(cards) { card ->
            Card(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(cardWidth)
                    .fillMaxHeight(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Upper Half: English Sentence
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.45f), // Slightly reduced height to make space for the divider
                        contentAlignment = Alignment.Center
                    ) {
                        DynamicText(
                            text = card.englishSentence,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxFontSize = 24.sp,
                            minFontSize = 10.sp
                        )
                    }

                    // Divider
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), // Add horizontal padding for better alignment
                        thickness = 1.dp, // Thickness of the divider
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) // Semi-transparent color
                    )

                    // Lower Half: Finnish Translation
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f), // Take up the remaining height
                        contentAlignment = Alignment.Center
                    ) {
                        DynamicText(
                            text = card.finnishTranslation.ifEmpty { "Translation not available" },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxFontSize = 24.sp,
                            minFontSize = 10.sp
                        )
                    }
                }
            }
        }

        item {
            IconButton(
                onClick = { onAddCardRequest(selectedTopic) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Card")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LingoCardsTheme {
//        InfoCards()
    }
}
