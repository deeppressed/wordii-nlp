package io.wordii.nlp.api.serialization

import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import io.wordii.nlp.api.Relation
import io.wordii.nlp.api.Word
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ValueGraphSerializer : KSerializer<ValueGraph<Word, Relation>> {
    override val descriptor: SerialDescriptor = ValueGraphSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): ValueGraph<Word, Relation> {
        val surrogate = decoder.decodeSerializableValue(ValueGraphSurrogate.serializer())
        val valueGraph = ValueGraphBuilder
            .directed()
            .build<Word, Relation>()
        for ((nodeId, connections) in surrogate.adjacency) {
            val node = surrogate.nodes[nodeId - 1]
            valueGraph.addNode(node)
            for ((childId, edgeValue) in connections) {
                val childNode = surrogate.nodes[childId - 1]
                valueGraph.putEdgeValue(node, childNode, edgeValue)
            }
        }
        return valueGraph
    }

    override fun serialize(encoder: Encoder, value: ValueGraph<Word, Relation>) {
        val roots = value.nodes().filter { value.inDegree(it) == 0 }
        val adjacency = mutableMapOf<Int, Set<Connection>>()
        for (root in roots) {
            visitNode(root, value, adjacency)
        }
        val nodes = value.nodes().sortedBy { it.position }
        val surrogate = ValueGraphSurrogate(nodes, adjacency)
        encoder.encodeSerializableValue(ValueGraphSurrogate.serializer(), surrogate)
    }

    private fun visitNode(
        root: Word, valueGraph: ValueGraph<Word, Relation>, adjacency: MutableMap<Int, Set<Connection>>
    ) {
        val connections = mutableSetOf<Connection>()
        val children = valueGraph.successors(root)
        for (child in children) {
            val edgeValue = valueGraph.edgeValue(root, child).orElse(null)
            connections.add(Connection(child.position, edgeValue))
            visitNode(child, valueGraph, adjacency)
        }
        adjacency[root.position] = connections
    }
}
