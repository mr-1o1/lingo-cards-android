package com.example.lingocards.data.api

import retrofit2.http.Body
import retrofit2.http.POST

data class TranslationRequest(
    val q: String,
    val source: String = "en",
    val target: String = "fi"
)

data class TranslationResponse(
    val translatedText: String
)

interface TranslationApi {
    @POST("translate")
    suspend fun translate(@Body request: TranslationRequest): TranslationResponse
} 