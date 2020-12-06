package io.wordii.nlp.api.serialization

import com.google.common.graph.ValueGraphBuilder
import io.wordii.nlp.api.Relation
import io.wordii.nlp.api.Word
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ValueGraphSerializerTest {
    @Test
    internal fun jsonSerializationTest() {
        val valueGraph = ValueGraphBuilder
            .directed()
            .build<Word, Relation>()
        val word1 = Word("First", "first", "--", 1)
        val word2 = Word("second", "second", "--", 2)
        val word3 = Word("third", "third", "--", 3)
        val word4 = Word("fourth", "fourth", "--", 4)
        val word5 = Word("fifth", "fifth", "--", 5)
        val dot = Word(".", ".", "--", 6)
        valueGraph.addNode(word1)
        valueGraph.addNode(word2)
        valueGraph.addNode(word3)
        valueGraph.addNode(word4)
        valueGraph.addNode(word5)
        valueGraph.addNode(dot)
        valueGraph.putEdgeValue(word1, word2, Relation.ACL)
        valueGraph.putEdgeValue(word1, word3, Relation.ADVCL)
        valueGraph.putEdgeValue(word2, word4, Relation.ADVMOD)
        valueGraph.putEdgeValue(word2, word5, Relation.AMOD)
        valueGraph.putEdgeValue(word3, word5, Relation.APPOS)
        valueGraph.putEdgeValue(word3, dot, Relation.PUNCT)
        val jsonValue = Json.encodeToJsonElement(ValueGraphSerializer, valueGraph)
        val decodedGraph = Json.decodeFromJsonElement(ValueGraphSerializer, jsonValue)
        assertEquals(valueGraph, decodedGraph)
    }
}
