package io.wordii.nlp.api

data class CoreferenceMention(val rawMention: String, val headWord: Word, val headWordPosition: WordPosition)
