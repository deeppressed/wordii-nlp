package io.wordii.nlp.api

import kotlinx.serialization.Serializable

// Note: simply pair of ints
@Serializable
data class WordPosition(private val sentenceNumber: Int, private val wordNumber: Int)
