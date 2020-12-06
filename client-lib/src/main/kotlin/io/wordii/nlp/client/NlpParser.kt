package io.wordii.nlp.client

import com.google.common.graph.ImmutableValueGraph
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import edu.stanford.nlp.coref.data.CorefChain
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.ling.IndexedWord
import edu.stanford.nlp.pipeline.CoreDocument
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.semgraph.SemanticGraph
import io.wordii.nlp.api.*
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import java.util.*

object NlpParser {
    private val pipeline: StanfordCoreNLP by lazy {
        val props = Properties()
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref")
        props.setProperty("coref.algorithm", "neural")
        StanfordCoreNLP(props)
    }

    fun parse(text: String?): SentenceSequence {
        if (text == null) {
            return SentenceSequence(emptyList(), emptySet())
        }
        val coreDocument = CoreDocument(text)
        pipeline.annotate(coreDocument)
        val sentences = coreDocument.sentences()
        val sentenceList = mutableListOf<Sentence>()
        for ((i, coreSentence) in sentences.withIndex()) {
            val semanticGraph = coreSentence.dependencyParse()
            val valueGraph = mapToValueGraph(semanticGraph)
            val roots = semanticGraph.roots
                .map { indexedWord: IndexedWord -> createWord(indexedWord) }
                .toImmutableList()
            val wordList = coreSentence.tokens()
                .map { coreLabel: CoreLabel -> createWord(coreLabel) }
                .toImmutableList()
            val sentence = Sentence(coreSentence.text(), valueGraph, roots, wordList, i + 1)
            sentenceList.add(sentence)
        }
        val coreferenceChains = coreDocument.corefChains()
            .map { (_, corefChain: CorefChain) ->
                CoreferenceChain(createMention(sentenceList, corefChain.representativeMention),
                    corefChain.mentionsInTextualOrder
                        .map { mention: CorefChain.CorefMention -> createMention(sentenceList, mention) }
                        .toImmutableList())
            }
            .toImmutableSet()
        return SentenceSequence(sentenceList.toImmutableList(), coreferenceChains)
    }

    private fun mapToValueGraph(semanticGraph: SemanticGraph): ValueGraph<Word, Relation> {
        val valueGraph = ValueGraphBuilder
            .directed()
            .build<Word, Relation>()
        for (root in semanticGraph.roots) {
            addChildNode(root, semanticGraph, valueGraph)
        }
        return ImmutableValueGraph.copyOf(valueGraph)
    }

    private fun addChildNode(
        root: IndexedWord,
        semanticGraph: SemanticGraph,
        valueGraph: MutableValueGraph<Word, Relation>
    ) {
        val rootCopy = createWord(root)
        valueGraph.addNode(rootCopy)
        if (semanticGraph.hasChildren(root)) {
            val children = semanticGraph.getChildren(root)
            for (child in children) {
                val edge = semanticGraph.getEdge(root, child)
                val relation = Relation.valueOf(edge.relation.shortName.toUpperCase())
                valueGraph.putEdgeValue(rootCopy, createWord(child), relation)
                addChildNode(child, semanticGraph, valueGraph)
            }
        }
    }

    private fun createWord(indexedWord: IndexedWord): Word {
        return createWord(indexedWord.backingLabel())
    }

    private fun createWord(coreLabel: CoreLabel): Word {
        return Word(coreLabel.originalText(), coreLabel.lemma(), coreLabel.tag(), coreLabel.index())
    }

    private fun createMention(sentenceList: List<Sentence>, mention: CorefChain.CorefMention): CoreferenceMention {
        val headWord = sentenceList[mention.sentNum - 1]
            .wordList[mention.headIndex - 1] // headIndex - head word of the mention
        return CoreferenceMention(mention.mentionSpan, headWord, WordPosition(mention.sentNum, mention.headIndex))
    }
}
