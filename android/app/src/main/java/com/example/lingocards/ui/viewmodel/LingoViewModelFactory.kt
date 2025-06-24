package com.example.lingocards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lingocards.data.repository.LingoRepository

class LingoViewModelFactory(
    private val repository: LingoRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LingoViewModel::class.java)) {
            return LingoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 