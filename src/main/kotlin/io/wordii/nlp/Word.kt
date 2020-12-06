package io.wordii.nlp

data class Word(
    val rawWord: String, val lemma: String, val partOfSpeech: String, // Position in sentence
    val position: Int
)
