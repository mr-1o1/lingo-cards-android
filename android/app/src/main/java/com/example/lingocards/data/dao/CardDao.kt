package com.example.lingocards.data.dao

import androidx.room.*
import com.example.lingocards.data.entity.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE topicId = :topicId ORDER BY createdAt ASC")
    fun getCardsByTopicId(topicId: Long): Flow<List<Card>>
    
    @Insert
    suspend fun insertCard(card: Card): Long
    
    @Update
    suspend fun updateCard(card: Card)
    
    @Delete
    suspend fun deleteCard(card: Card)
    
    @Query("SELECT * FROM cards WHERE id = :cardId")
    suspend fun getCardById(cardId: Long): Card?
} 