package io.wordii.nlp.api

import kotlinx.serialization.Serializable

@Serializable
data class CoreferenceChain(val mainMention: CoreferenceMention, val mentions: List<CoreferenceMention>)
