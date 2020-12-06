package io.wordii.nlp;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class SentenceSequence {
    @NotNull
    private final List<Sentence> sentenceList;
    @NotNull
    private final Set<CoreferenceChain> coreferenceChains;

    public SentenceSequence(@NotNull List<Sentence> sentenceList, @NotNull Set<CoreferenceChain> coreferenceChains) {
        this.sentenceList = sentenceList;
        this.coreferenceChains = coreferenceChains;
    }

    @NotNull
    public List<Sentence> getSentenceList() {
        return sentenceList;
    }

    @NotNull
    public Set<CoreferenceChain> findCorefChains(@NotNull WordPosition position) {
        return coreferenceChains.stream()
                .filter(chain -> chain.getMentions().stream()
                        .anyMatch(mention -> position.equals(mention.getHeadWordPosition())))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentenceSequence sentences = (SentenceSequence) o;
        return sentenceList.equals(sentences.sentenceList) &&
                coreferenceChains.equals(sentences.coreferenceChains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentenceList, coreferenceChains);
    }

    @Override
    public String toString() {
        return "SentenceSequence{" +
                "sentenceList=" + sentenceList +
                ", coreferenceChains=" + coreferenceChains +
                '}';
    }
}
