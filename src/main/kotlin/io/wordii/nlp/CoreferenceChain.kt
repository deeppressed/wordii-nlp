package io.wordii.nlp

data class CoreferenceChain(val mainMention: CoreferenceMention, val mentions: List<CoreferenceMention>)
