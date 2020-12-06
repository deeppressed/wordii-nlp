package io.wordii.nlp;

import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public final class NlpParser {
    private final StanfordCoreNLP pipeline;

    private NlpParser() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");
        props.setProperty("coref.algorithm", "neural");
        pipeline = new StanfordCoreNLP(props);
    }

    public static NlpParser getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final NlpParser INSTANCE = new NlpParser();
    }

    public SentenceSequence parse(String text) {
        CoreDocument coreDocument = new CoreDocument(text);
        pipeline.annotate(coreDocument);

        List<CoreSentence> sentences = coreDocument.sentences();
        List<Sentence> sentenceList = new ArrayList<>(sentences.size());
        for (int i = 0; i < sentences.size(); i++) {
            CoreSentence coreSentence = sentences.get(i);
            SemanticGraph semanticGraph = coreSentence.dependencyParse();
            ValueGraph<Word, Relation> valueGraph = mapToValueGraph(semanticGraph);
            List<Word> roots = semanticGraph.getRoots().stream()
                    .map(NlpParser::createWord)
                    .collect(Collectors.toUnmodifiableList());
            List<Word> wordList = coreSentence.tokens().stream()
                    .map(NlpParser::createWord)
                    .collect(Collectors.toUnmodifiableList());
            Sentence sentence = new Sentence(coreSentence.text(), valueGraph, roots, wordList, i + 1);
            sentenceList.add(sentence);
        }
        Set<CoreferenceChain> coreferenceChains = coreDocument.corefChains().values().stream()
                .map(corefChain -> new CoreferenceChain(createMention(sentenceList, corefChain.getRepresentativeMention()),
                        corefChain.getMentionsInTextualOrder().stream()
                                .map(mention -> createMention(sentenceList, mention))
                                .collect(Collectors.toUnmodifiableList())))
                .collect(Collectors.toUnmodifiableSet());
        return new SentenceSequence(Collections.unmodifiableList(sentenceList), coreferenceChains);
    }

    @NotNull
    private static ValueGraph<Word, Relation> mapToValueGraph(SemanticGraph semanticGraph) {
        MutableValueGraph<Word, Relation> valueGraph = ValueGraphBuilder
                .directed()
                .build();
        for (IndexedWord root : semanticGraph.getRoots()) {
            addChildNode(root, semanticGraph, valueGraph);
        }
        return ImmutableValueGraph.copyOf(valueGraph);
    }

    private static void addChildNode(IndexedWord root, SemanticGraph semanticGraph, MutableValueGraph<Word, Relation> valueGraph) {
        Word rootCopy = createWord(root);
        valueGraph.addNode(rootCopy);
        if (semanticGraph.hasChildren(root)) {
            Set<IndexedWord> children = semanticGraph.getChildren(root);
            for (IndexedWord child : children) {
                SemanticGraphEdge edge = semanticGraph.getEdge(root, child);
                Relation relation = Relation.valueOf(edge.getRelation().getShortName().toUpperCase());
                valueGraph.putEdgeValue(rootCopy, createWord(child), relation);
                addChildNode(child, semanticGraph, valueGraph);
            }
        }
    }

    @NotNull
    private static Word createWord(IndexedWord indexedWord) {
        return createWord(indexedWord.backingLabel());
    }

    @NotNull
    private static Word createWord(CoreLabel coreLabel) {
        return new Word(coreLabel.originalText(), coreLabel.lemma(), coreLabel.tag(), coreLabel.index());
    }

    @NotNull
    private static CoreferenceMention createMention(List<Sentence> sentenceList, CorefChain.CorefMention mention) {
        Word headWord = sentenceList.get(mention.sentNum - 1)
                .getWordList().get(mention.headIndex - 1); // headIndex - head word of the mention
        return new CoreferenceMention(mention.mentionSpan, headWord, new WordPosition(mention.sentNum, mention.headIndex));
    }
}
