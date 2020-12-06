package io.wordii.nlp.api

data class CoreferenceChain(val mainMention: CoreferenceMention, val mentions: List<CoreferenceMention>)
