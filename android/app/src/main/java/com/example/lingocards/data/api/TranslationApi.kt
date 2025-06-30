package com.example.lingocards.data.api

import retrofit2.http.Body
import retrofit2.http.POST

data class TranslationRequest(
    val sentence: String,
    val source_language: String = "en",
    val target_language: String = "fi"
)

data class TranslationResponse(
    val translated_sentence: String
)

interface TranslationApi {
    @POST("translate")
    suspend fun translate(@Body request: TranslationRequest): TranslationResponse
} 