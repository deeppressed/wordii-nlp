package io.wordii.nlp;

import java.util.Objects;

// Note: simply pair of ints
public final class WordPosition {
    private final int sentenceNumber;
    private final int wordNumber;

    public WordPosition(int sentenceNumber, int wordNumber) {
        this.sentenceNumber = sentenceNumber;
        this.wordNumber = wordNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordPosition wordPosition = (WordPosition) o;
        return sentenceNumber == wordPosition.sentenceNumber &&
                wordNumber == wordPosition.wordNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentenceNumber, wordNumber);
    }

    @Override
    public String toString() {
        return "WordPosition{" +
                "sentenceNumber=" + sentenceNumber +
                ", wordNumber=" + wordNumber +
                '}';
    }
}
