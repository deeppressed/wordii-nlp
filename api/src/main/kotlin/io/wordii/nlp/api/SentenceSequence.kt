package io.wordii.nlp.api

import kotlinx.collections.immutable.toImmutableSet
import kotlinx.serialization.Serializable

@Serializable
data class SentenceSequence(
        val sentenceList: List<Sentence>,
        private val coreferenceChains: Set<CoreferenceChain>
) {
    fun findCorefChains(position: WordPosition): Set<CoreferenceChain> {
        return coreferenceChains.filter { chain: CoreferenceChain ->
            chain.mentions.any { mention: CoreferenceMention -> position == mention.headWordPosition }
        }.toImmutableSet()
    }
}
