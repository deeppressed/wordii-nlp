package io.wordii.nlp.api

import kotlinx.serialization.Serializable

/**
 * UD v2 - https://universaldependencies.org/u/dep/all.html.
 */
@Serializable
enum class Relation(val udName: String) {
    NSUBJ("nsubj"),
    OBJ("obj"),
    IOBJ("iobj"),
    CSUBJ("csubj"),
    CCOMP("ccomp"),
    XCOMP("xcomp"),
    OBL("obl"),
    VOCATIVE("vocative"),
    EXPL("expl"),
    DISLOCATED("dislocated"),
    ADVCL("advcl"),
    ADVMOD("advmod"),
    DISCOURSE("discourse"),
    AUX("aux"),
    COP("cop"),
    MARK("mark"),
    NMOD("nmod"),
    APPOS("appos"),
    NUMMOD("nummod"),
    ACL("acl"),
    AMOD("amod"),
    DET("det"),
    CLF("clf"),
    CASE("case"),
    CONJ("conj"),
    CC("cc"),
    FIXED("fixed"),
    FLAT("flat"),
    COMPOUND("compound"),
    LIST("list"),
    PARATAXIS("parataxis"),
    ORPHAN("orphan"),
    GOESWITH("goeswith"),
    REPARANDUM("reparandum"),
    PUNCT("punct"),
    ROOT("root"),
    DEP("dep");
}
