package com.example.lingocards.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lingocards.data.dao.CardDao
import com.example.lingocards.data.dao.TopicDao
import com.example.lingocards.data.entity.Card
import com.example.lingocards.data.entity.Topic

@Database(
    entities = [Topic::class, Card::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun cardDao(): CardDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lingo_cards_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 