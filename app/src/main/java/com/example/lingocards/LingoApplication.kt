package com.example.lingocards

import android.app.Application
import com.example.lingocards.data.AppDatabase
import com.example.lingocards.data.DatabaseInitializer
import com.example.lingocards.data.repository.LingoRepository

class LingoApplication : Application() {
    
    lateinit var database: AppDatabase
        private set
    
    lateinit var repository: LingoRepository
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize database
        database = AppDatabase.getDatabase(this)
        
        // Initialize repository
        repository = LingoRepository(
            topicDao = database.topicDao(),
            cardDao = database.cardDao()
        )
        
        // Initialize database with sample data
        DatabaseInitializer.initializeDatabase(database)
    }
} 