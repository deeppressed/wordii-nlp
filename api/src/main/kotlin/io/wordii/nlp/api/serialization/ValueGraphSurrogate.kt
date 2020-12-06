package io.wordii.nlp.api.serialization

import io.wordii.nlp.api.Word
import kotlinx.serialization.Serializable

@Serializable
class ValueGraphSurrogate(val nodes: List<Word>, val adjacency: Map<Int, Set<Connection>>)
