package io.wordii.nlp;

import java.util.Objects;

public final class CoreferenceMention {
    private final String rawMention;
    private final Word headWord;
    private final WordPosition headWordPosition;

    public CoreferenceMention(String rawMention, Word headWord, WordPosition headWordPosition) {
        this.rawMention = rawMention;
        this.headWord = headWord;
        this.headWordPosition = headWordPosition;
    }

    public String getRawMention() {
        return rawMention;
    }

    public Word getHeadWord() {
        return headWord;
    }

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
