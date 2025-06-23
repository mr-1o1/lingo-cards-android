package com.example.lingocards.data

import com.example.lingocards.data.entity.Card
import com.example.lingocards.data.entity.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object DatabaseInitializer {
    
    fun initializeDatabase(database: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            val topicDao = database.topicDao()
            val cardDao = database.cardDao()
            
            // Check if database is empty
            val existingTopics = topicDao.getAllTopics().first()
            if (existingTopics.isEmpty()) {
                // Add sample topics
                val groceryTopicId = topicDao.insertTopic(Topic(name = "Grocery"))
                val casualTopicId = topicDao.insertTopic(Topic(name = "Casual"))
                val businessTopicId = topicDao.insertTopic(Topic(name = "Business"))
                
                // Add sample cards for Grocery topic
                val groceryCards = listOf(
                    Card(topicId = groceryTopicId, englishText = "I need to buy some milk", finnishText = "Minun täytyy ostaa maitoa"),
                    Card(topicId = groceryTopicId, englishText = "Where is the bread aisle?", finnishText = "Missä on leipähylly?"),
                    Card(topicId = groceryTopicId, englishText = "How much does this cost?", finnishText = "Paljonko tämä maksaa?"),
                    Card(topicId = groceryTopicId, englishText = "Do you have fresh vegetables?", finnishText = "Onko teillä tuoreita vihanneksia?")
                )
                
                // Add sample cards for Casual topic
                val casualCards = listOf(
                    Card(topicId = casualTopicId, englishText = "How are you doing?", finnishText = "Miten menee?"),
                    Card(topicId = casualTopicId, englishText = "What's the weather like today?", finnishText = "Minkälainen sää on tänään?"),
                    Card(topicId = casualTopicId, englishText = "Let's go for a walk", finnishText = "Mennään kävelylle"),
                    Card(topicId = casualTopicId, englishText = "I'm tired", finnishText = "Olen väsynyt")
                )
                
                // Add sample cards for Business topic
                val businessCards = listOf(
                    Card(topicId = businessTopicId, englishText = "I have a meeting at 2 PM", finnishText = "Minulla on kokous kello 14:00"),
                    Card(topicId = businessTopicId, englishText = "Can you send me the report?", finnishText = "Voitko lähettää minulle raportin?"),
                    Card(topicId = businessTopicId, englishText = "The deadline is next Friday", finnishText = "Määräaika on ensi perjantaina"),
                    Card(topicId = businessTopicId, englishText = "I need to schedule a call", finnishText = "Minun täytyy ajoittaa puhelu")
                )
                
                // Insert all cards
                (groceryCards + casualCards + businessCards).forEach { card ->
                    cardDao.insertCard(card)
                }
            }
        }
    }
} 