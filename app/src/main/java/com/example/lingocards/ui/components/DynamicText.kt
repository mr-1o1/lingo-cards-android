package com.example.lingocards.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DynamicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxFontSize: TextUnit = 24.sp,
    minFontSize: TextUnit = 10.sp
) {
    var fontSize by remember { mutableStateOf(maxFontSize) }
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier.fillMaxSize(), 
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style.copy(fontSize = fontSize),
            color = color,
            textAlign = TextAlign.Center,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.didOverflowWidth || textLayoutResult.didOverflowHeight) {
                    fontSize = (fontSize.value - 1).sp // Reduce font size if text overflows
                }
            }
        )
    }

    // Ensure font size doesn't go below the minimum
    LaunchedEffect(fontSize) {
        if (fontSize < minFontSize) {
            fontSize = minFontSize
        }
    }
}