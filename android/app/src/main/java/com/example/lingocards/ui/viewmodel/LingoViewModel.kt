package com.example.lingocards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lingocards.data.entity.Card
import com.example.lingocards.data.entity.Topic
import com.example.lingocards.data.repository.LingoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LingoUiState(
    val topics: List<Topic> = emptyList(),
    val selectedTopicId: Long? = null,
    val cardsByTopic: Map<Long, List<Card>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAddingTopic: Boolean = false,
    val isAddingCard: Boolean = false
)

class LingoViewModel(
    private val repository: LingoRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LingoUiState())
    val uiState: StateFlow<LingoUiState> = _uiState.asStateFlow()
    
    init {
        loadTopics()
    }
    
    private fun loadTopics() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repository.getAllTopics().collect { topics ->
                    _uiState.value = _uiState.value.copy(
                        topics = topics,
                        selectedTopicId = topics.firstOrNull()?.id,
                        isLoading = false
                    )
                    // Load cards for each topic
                    topics.forEach { topic ->
                        loadCardsForTopic(topic.id)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    
    private fun loadCardsForTopic(topicId: Long) {
        viewModelScope.launch {
            try {
                repository.getCardsByTopicId(topicId).collect { cards ->
                    val currentCardsByTopic = _uiState.value.cardsByTopic.toMutableMap()
                    currentCardsByTopic[topicId] = cards
                    _uiState.value = _uiState.value.copy(
                        cardsByTopic = currentCardsByTopic
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }
    
    fun selectTopic(topicId: Long) {
        _uiState.value = _uiState.value.copy(selectedTopicId = topicId)
    }
    
    fun addTopic(name: String) {
        if (name.isBlank()) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAddingTopic = true)
            try {
                val topic = Topic(name = name.trim())
                val topicId = repository.insertTopic(topic)
                _uiState.value = _uiState.value.copy(
                    isAddingTopic = false,
                    selectedTopicId = topicId
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isAddingTopic = false
                )
            }
        }
    }
    
    fun updateTopic(topic: Topic, newName: String) {
        if (newName.isBlank()) return
        
        viewModelScope.launch {
            try {
                val updatedTopic = topic.copy(name = newName.trim())
                repository.updateTopic(updatedTopic)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun deleteTopic(topic: Topic) {
        viewModelScope.launch {
            try {
                repository.deleteTopic(topic)
                val currentCardsByTopic = _uiState.value.cardsByTopic.toMutableMap()
                currentCardsByTopic.remove(topic.id)
                _uiState.value = _uiState.value.copy(
                    cardsByTopic = currentCardsByTopic,
                    selectedTopicId = _uiState.value.topics.firstOrNull { it.id != topic.id }?.id
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun addCard(topicId: Long, englishText: String) {
        if (englishText.isBlank()) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAddingCard = true)
            try {
                val result = repository.createCardWithTranslation(topicId, englishText.trim())
                result.fold(
                    onSuccess = { card ->
                        _uiState.value = _uiState.value.copy(isAddingCard = false)
                    },
                    onFailure = { exception ->
                        _uiState.value = _uiState.value.copy(
                            error = exception.message,
                            isAddingCard = false
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isAddingCard = false
                )
            }
        }
    }
    
    fun updateCard(card: Card, englishText: String, finnishText: String) {
        if (englishText.isBlank() || finnishText.isBlank()) return
        
        viewModelScope.launch {
            try {
                val updatedCard = card.copy(
                    englishText = englishText.trim(),
                    finnishText = finnishText.trim()
                )
                repository.updateCard(updatedCard)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun deleteCard(card: Card) {
        viewModelScope.launch {
            try {
                repository.deleteCard(card)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
} 