package io.wordii.nlp

import kotlinx.collections.immutable.toImmutableSet

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
