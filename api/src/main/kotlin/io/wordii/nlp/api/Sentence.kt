package io.wordii.nlp.api

import com.google.common.graph.ValueGraph
import io.wordii.nlp.api.serialization.ValueGraphSerializer
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import java.util.regex.Pattern

@Serializable
data class Sentence(
    val rawText: String,
    @Serializable(with = ValueGraphSerializer::class)
    private val dependencyGraph: ValueGraph<Word, Relation>,
    val roots: List<Word>,
    // Note: words and punctuations (aka tokens)
    val wordList: List<Word>,
    // Position in text
    val position: Int
) {
    fun children(node: Word): Set<Word> {
        return dependencyGraph.successors(node)
    }

    fun childWithEdgeValue(node: Word, edgeValue: Relation): Word? {
        val successors = dependencyGraph.successors(node)
        for (successor in successors) {
            if (edgeValue == edgeValue(node, successor)) {
                return successor
            }
        }
        return null
    }

    fun edgeValue(nodeU: Word, nodeV: Word): Relation? {
        return dependencyGraph.edgeValue(nodeU, nodeV).orElse(null)
    }

    fun hasDirectSpeech(): Boolean {
        return PATTERN.matcher(rawText).find()
    }

    fun allDirectSpeeches(): List<String> {
        if (!hasDirectSpeech()) {
            return emptyList()
        }
        val matcher = PATTERN.matcher(rawText)
        val speeches = mutableListOf<String>()
        while (matcher.find()) {
            val group = matcher.group()
            speeches.add(group.substring(1, group.length - 1))
        }
        return speeches.toImmutableList()
    }

    companion object {
        private val PATTERN = Pattern.compile("\".+\"")
    }
}
