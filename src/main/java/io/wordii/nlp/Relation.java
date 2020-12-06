package io.wordii.nlp;

import org.jetbrains.annotations.NotNull;

/**
 * UD v1 - https://universaldependencies.org/docsv1/u/dep/index.html.
 */
@SuppressWarnings("unused")
public enum Relation {
    ACL("acl"),
    ADVCL("advcl"),
    ADVMOD("advmod"),
    AMOD("amod"),
    APPOS("appos"),
    AUX("aux"),
    AUXPASS("auxpass"),
    CASE("case"),
    CC("cc"),
    CCOMP("ccomp"),
    COMPOUND("compound"),
    CONJ("conj"),
    COP("cop"),
    CSUBJ("csubj"),
    CSUBJPASS("csubjpass"),
    DEP("dep"),
    DET("det"),
    DISCOURSE("discourse"),
    DISLOCATED("dislocated"),
    DOBJ("dobj"),
    EXPL("expl"),
    FOREIGN("foreign"),
    GOESWITH("goeswith"),
    IOBJ("iobj"),
    LIST("list"),
    MARK("mark"),
    MWE("mwe"),
    NAME("name"),
    NEG("neg"),
    NMOD("nmod"),
    NSUBJ("nsubj"),
    NSUBJPASS("nsubjpass"),
    NUMMOD("nummod"),
    PARATAXIS("parataxis"),
    PUNCT("punct"),
    REMNANT("remnant"),
    REPARANDUM("reparandum"),
    ROOT("root"),
    VOCATIVE("vocative"),
    XCOMP("xcomp");

    private final String udName;

    Relation(String udName) {
        this.udName = udName;
    }

    @NotNull
    public String getUdName() {
        return udName;
    }
}
