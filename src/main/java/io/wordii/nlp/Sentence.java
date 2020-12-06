package io.wordii.nlp;

import com.google.common.graph.ValueGraph;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Sentence {
    private static final Pattern PATTERN = Pattern.compile("\".+\"");

    private final String rawText;
    private final ValueGraph<Word, Relation> dependencyTree;
    private final List<Word> roots;
    // Note: words and punctuations (aka tokens)
    private final List<Word> wordList;
    // Position in text
    private final int position;

    public Sentence(String rawText, ValueGraph<Word, Relation> dependencyTree, List<Word> roots, List<Word> wordList, int position) {
        this.rawText = rawText;
        this.dependencyTree = dependencyTree;
        this.roots = roots;
        this.wordList = wordList;
        this.position = position;
    }

    public Set<Word> children(@NotNull Word node) {
        return dependencyTree.successors(node);
    }

    @Nullable
    public Word childWithEdgeValue(@NotNull Word node, @NotNull Relation edgeValue) {
        Set<Word> successors = dependencyTree.successors(node);
        for (Word successor : successors) {
            if (edgeValue.equals(edgeValue(node, successor))) {
                return successor;
            }
        }
        return null;
    }

    @Nullable
    public Relation edgeValue(@NotNull Word nodeU, @NotNull Word nodeV) {
        return dependencyTree.edgeValue(nodeU, nodeV).orElse(null);
    }

    public boolean hasDirectSpeech() {
        return PATTERN.matcher(rawText).find();
    }

    public List<String> getDirectSpeeches() {
        if (!hasDirectSpeech()) {
            return Collections.emptyList();
        }
        Matcher matcher = PATTERN.matcher(rawText);
        List<String> speeches = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            speeches.add(group.substring(1, group.length() - 1));
        }
        return Collections.unmodifiableList(speeches);
    }

    public String getRawText() {
        return rawText;
    }

    public List<Word> getRoots() {
        return roots;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return rawText.equals(sentence.rawText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawText);
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "rawText='" + rawText + '\'' +
                ", dependencyTree=" + dependencyTree +
                ", roots=" + roots +
                ", wordList=" + wordList +
                ", position=" + position +
                '}';
    }
}
