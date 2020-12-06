package io.wordii.nlp;

import java.util.List;
import java.util.Objects;

public final class CoreferenceChain {
    private final CoreferenceMention mainMention;
    private final List<CoreferenceMention> mentions;

    public CoreferenceChain(CoreferenceMention mainMention, List<CoreferenceMention> mentions) {
        this.mainMention = mainMention;
        this.mentions = mentions;
    }

    public CoreferenceMention getMainMention() {
        return mainMention;
    }

    public List<CoreferenceMention> getMentions() {
        return mentions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoreferenceChain coreferenceChain = (CoreferenceChain) o;
        return mainMention.equals(coreferenceChain.mainMention) &&
                mentions.equals(coreferenceChain.mentions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainMention, mentions);
    }

    @Override
    public String toString() {
        return "CoreferenceChain{" +
                "mainMention=" + mainMention +
                ", mentions=" + mentions +
                '}';
    }
}
