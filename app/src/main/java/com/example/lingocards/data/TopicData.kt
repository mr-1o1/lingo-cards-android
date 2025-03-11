package com.example.lingocards.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf

object TopicData1 {
    val topics = listOf("Topic 1", "Topic 2", "Topic 3", "Topic 4", "Topic 5")

    val topicInfoCards = SentenceData.topicInfoCards
}

object TopicData {
    val topics = mutableStateListOf("Grocery", "Transaction", "Casual Speaking")

    val topicInfoCards = mutableStateMapOf(
        "Grocery" to mutableStateListOf(
            CardData(englishSentence = "I need apples.", finnishTranslation = "Minulla on omenat."),
            CardData(englishSentence = "How much does this cost?", finnishTranslation = "Kuinka paljon tämä maksaa?"),
            CardData(englishSentence = "Do you have fresh vegetables?", finnishTranslation = "Onko teillä tuoreita vihanneksia?"),
            CardData(englishSentence = "Where is the dairy section?", finnishTranslation = "Missä on maitotuotteiden osasto?"),
            CardData(englishSentence = "Can I get a discount?", finnishTranslation = "Voinko saada alennusta?")
        ),
        "Transaction" to mutableStateListOf(
            CardData(englishSentence = "How can I pay?", finnishTranslation = "Miten voin maksaa?"),
            CardData(englishSentence = "Do you accept credit cards?", finnishTranslation = "Hyväksytkö luottokortteja?"),
            CardData(englishSentence = "The total is 50 euros.", finnishTranslation = "Yhteensä 50 euroa."),
            CardData(englishSentence = "Can I have a receipt?", finnishTranslation = "Voinko saada kuittin?"),
            CardData(englishSentence = "Is there a service charge?", finnishTranslation = "Onko palvelumaksu?")
        ),
        "Casual Speaking" to mutableStateListOf(
            CardData(englishSentence = "How are you?", finnishTranslation = "Mitä kuuluu?"),
            CardData(englishSentence = "Nice to meet you.", finnishTranslation = "Hauska tavata."),
            CardData(englishSentence = "What is your name?", finnishTranslation = "Mikä on nimesi?"),
            CardData(englishSentence = "Where are you from?", finnishTranslation = "Mistä olet kotoisin?"),
            CardData(englishSentence = "Thank you very much.", finnishTranslation = "Kiitos paljon.")
        )
    )
}

