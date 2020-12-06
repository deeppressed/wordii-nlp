package io.wordii.nlp.api

interface NlpParser {
    fun depParse(text: String?): SentenceSequence
}
