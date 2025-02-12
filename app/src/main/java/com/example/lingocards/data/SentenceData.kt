package com.example.lingocards.data

object SentenceData {
    val topicInfoCards = mapOf(
        "Topic 1" to listOf(
            CardData(englishSentence = "Hello", finnishTranslation = "Hei"),
            CardData(englishSentence = "Good morning", finnishTranslation = "Hyvää huomenta"),
            CardData(englishSentence = "Thank you", finnishTranslation = "Kiitos")
        ),
        "Topic 2" to listOf(
            CardData(englishSentence = "How are you?", finnishTranslation = "Mitä kuuluu?"),
            CardData(englishSentence = "I am fine", finnishTranslation = "Minulla menee hyvin"),
            CardData(englishSentence = "See you later", finnishTranslation = "Nähdään myöhemmin")
        ),
        // Add more topics here...
    )
}
