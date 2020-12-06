package io.wordii.nlp

data class CoreferenceMention(val rawMention: String, val headWord: Word, val headWordPosition: WordPosition)
