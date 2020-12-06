package io.wordii.nlp;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Word {
    @NotNull
    private final String rawWord;
    @NotNull
    private final String lemma;
    @NotNull
    private final String partOfSpeech;
    // Position in sentence
    private final int position;

    public Word(@NotNull String rawWord, @NotNull String lemma, @NotNull String partOfSpeech, int position) {
        this.rawWord = rawWord;
        this.lemma = lemma;
        this.partOfSpeech = partOfSpeech;
        this.position = position;
    }

    @NotNull
    public String getRawWord() {
        return rawWord;
    }

    @NotNull
    public String getLemma() {
        return lemma;
    }

    @NotNull
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return rawWord.equals(word.rawWord) &&
                lemma.equals(word.lemma) &&
                partOfSpeech.equals(word.partOfSpeech);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawWord, lemma, partOfSpeech);
    }

    @Override
    public String toString() {
        return "Word{" +
                "rawWord='" + rawWord + '\'' +
                ", lemma='" + lemma + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", position=" + position +
                '}';
    }
}
