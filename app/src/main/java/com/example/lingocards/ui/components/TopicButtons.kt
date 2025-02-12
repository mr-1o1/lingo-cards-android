package com.example.lingocards.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopicButtons(
    topics: List<String>,
    selectedTopic: String,
    onTopicSelected: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .statusBarsPadding()
    ) {
        items(topics) { topic ->
            val isSelected = topic == selectedTopic
            Button(
                onClick = { onTopicSelected(topic) },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .heightIn(min = 48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = topic,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
            }
        }
    }

//    Row(
//        modifier = Modifier
//            .horizontalScroll(scrollState)
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .statusBarsPadding()
//    ) {
//        topics.forEach { topic ->
//            val isSelected = topic == selectedTopic
//            Button(
//                onClick = { onTopicSelected(topic) },
//                modifier = Modifier
//                    .padding(end = 8.dp)
//                    .heightIn(min = 48.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
//                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
//                ),
//                shape = RoundedCornerShape(24.dp) // Rounded corners
//            ) {
//                Text(
//                    text = topic,
//                    style = MaterialTheme.typography.labelMedium,
//                    maxLines = 1
//                )
//            }
//        }
//    }
}
