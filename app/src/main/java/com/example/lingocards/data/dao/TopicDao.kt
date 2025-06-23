package com.example.lingocards.data.dao

import androidx.room.*
import com.example.lingocards.data.entity.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics ORDER BY createdAt ASC")
    fun getAllTopics(): Flow<List<Topic>>
    
    @Insert
    suspend fun insertTopic(topic: Topic): Long
    
    @Update
    suspend fun updateTopic(topic: Topic)
    
    @Delete
    suspend fun deleteTopic(topic: Topic)
    
    @Query("SELECT * FROM topics WHERE id = :topicId")
    suspend fun getTopicById(topicId: Long): Topic?
} 