package io.wordii.nlp;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class CoreferenceMention {
    @NotNull
    private final String rawMention;
    @NotNull
    private final Word headWord;
    @NotNull
    private final WordPosition headWordPosition;

    public CoreferenceMention(@NotNull String rawMention, @NotNull Word headWord, @NotNull WordPosition headWordPosition) {
        this.rawMention = rawMention;
        this.headWord = headWord;
        this.headWordPosition = headWordPosition;
    }

    @NotNull
    public String getRawMention() {
        return rawMention;
    }

    @NotNull
    public Word getHeadWord() {
        return headWord;
    }

    @NotNull
    public WordPosition getHeadWordPosition() {
        return headWordPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoreferenceMention coreferenceMention = (CoreferenceMention) o;
        return rawMention.equals(coreferenceMention.rawMention) &&
                headWord.equals(coreferenceMention.headWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMention, headWord);
    }

    @Override
    public String toString() {
        return "CoreferenceMention{" +
                "rawMention='" + rawMention + '\'' +
                ", headWord=" + headWord +
                ", headWordPosition=" + headWordPosition +
                '}';
    }
}
