package com.example.lingocards.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingocards.data.entity.Card
import com.example.lingocards.data.entity.Topic
import com.example.lingocards.ui.components.CardComponent
import com.example.lingocards.ui.components.TopicComponent
import com.example.lingocards.ui.viewmodel.LingoUiState
import com.example.lingocards.ui.viewmodel.LingoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: LingoUiState,
    viewModel: LingoViewModel,
    modifier: Modifier = Modifier
) {
    var showAddCardDialog by remember { mutableStateOf(false) }
    var newCardText by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lingo Cards",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Topics section
            TopicComponent(
                topics = uiState.topics,
                selectedTopicId = uiState.selectedTopicId,
                onTopicSelected = { topicId -> viewModel.selectTopic(topicId) },
                onAddTopic = { name -> viewModel.addTopic(name) },
                onEditTopic = { topic, newName -> viewModel.updateTopic(topic, newName) },
                onDeleteTopic = { topic -> viewModel.deleteTopic(topic) },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Cards section
            uiState.selectedTopicId?.let { selectedTopicId ->
                val selectedTopic = uiState.topics.find { it.id == selectedTopicId }
                val cards = uiState.cardsByTopic[selectedTopicId] ?: emptyList()
                
                // Cards header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${selectedTopic?.name ?: "Unknown"} Cards",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Button(
                        onClick = { showAddCardDialog = true },
                        enabled = !uiState.isAddingCard
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add card"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Card")
                    }
                }
                
                // Cards list
                if (cards.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No cards yet. Add your first card!",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(cards) { card ->
                            CardComponent(
                                card = card,
                                onEdit = { card, englishText, finnishText ->
                                    viewModel.updateCard(card, englishText, finnishText)
                                },
                                onDelete = { card ->
                                    viewModel.deleteCard(card)
                                }
                            )
                        }
                    }
                }
            } ?: run {
                // No topic selected
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Select a topic to view cards",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
    
    // Add card dialog
    if (showAddCardDialog) {
        AlertDialog(
            onDismissRequest = { showAddCardDialog = false },
            title = { Text("Add New Card") },
            text = {
                OutlinedTextField(
                    value = newCardText,
                    onValueChange = { newCardText = it },
                    label = { Text("English Text") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter English text to translate") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newCardText.isNotBlank() && uiState.selectedTopicId != null) {
                            viewModel.addCard(uiState.selectedTopicId, newCardText)
                            newCardText = ""
                            showAddCardDialog = false
                        }
                    },
                    enabled = newCardText.isNotBlank() && !uiState.isAddingCard
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        newCardText = ""
                        showAddCardDialog = false 
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Error dialog
    uiState.error?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.clearError() }
                ) {
                    Text("OK")
                }
            }
        )
    }
    
    // Loading indicator
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
} 