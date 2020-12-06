package io.wordii.nlp.api.serialization

import io.wordii.nlp.api.Relation
import kotlinx.serialization.Serializable

@Serializable
data class Connection(val childId: Int, val edgeValue: Relation)
