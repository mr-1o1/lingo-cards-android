package com.example.lingocards.data.repository

import com.example.lingocards.data.api.ApiClient
import com.example.lingocards.data.api.TranslationRequest
import com.example.lingocards.data.dao.CardDao
import com.example.lingocards.data.dao.TopicDao
import com.example.lingocards.data.entity.Card
import com.example.lingocards.data.entity.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LingoRepository(
    private val topicDao: TopicDao,
    private val cardDao: CardDao
) {
    // Topic operations
    fun getAllTopics(): Flow<List<Topic>> = topicDao.getAllTopics()
    
    suspend fun insertTopic(topic: Topic): Long = topicDao.insertTopic(topic)
    
    suspend fun updateTopic(topic: Topic) = topicDao.updateTopic(topic)
    
    suspend fun deleteTopic(topic: Topic) = topicDao.deleteTopic(topic)
    
    suspend fun getTopicById(topicId: Long): Topic? = topicDao.getTopicById(topicId)
    
    // Card operations
    fun getCardsByTopicId(topicId: Long): Flow<List<Card>> = cardDao.getCardsByTopicId(topicId)
    
    suspend fun insertCard(card: Card): Long = cardDao.insertCard(card)
    
    suspend fun updateCard(card: Card) = cardDao.updateCard(card)
    
    suspend fun deleteCard(card: Card) = cardDao.deleteCard(card)
    
    suspend fun getCardById(cardId: Long): Card? = cardDao.getCardById(cardId)
    
    // Translation operations
    suspend fun translateToFinnish(englishText: String): Result<String> = runCatching {
        val request = TranslationRequest(q = englishText)
        val response = ApiClient.translationApi.translate(request)
        response.translatedText
    }
    
    // Combined operations
    suspend fun createCardWithTranslation(topicId: Long, englishText: String): Result<Card> = runCatching {
        val translation = translateToFinnish(englishText).getOrThrow()
        val card = Card(
            topicId = topicId,
            englishText = englishText,
            finnishText = translation
        )
        val cardId = insertCard(card)
        card.copy(id = cardId)
    }
} 