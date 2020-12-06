package io.wordii.nlp.api

import kotlinx.serialization.Serializable

@Serializable
data class CoreferenceMention(val rawMention: String, val headWord: Word, val headWordPosition: WordPosition)
