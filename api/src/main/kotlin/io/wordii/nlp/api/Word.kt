package io.wordii.nlp.api

data class Word(
    val rawWord: String, val lemma: String, val partOfSpeech: String, // Position in sentence
    val position: Int
)
