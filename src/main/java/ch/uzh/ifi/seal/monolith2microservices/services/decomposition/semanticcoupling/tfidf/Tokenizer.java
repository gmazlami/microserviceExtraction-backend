package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf;

import java.util.List;

/**
 * Break text into tokens
 */
public interface Tokenizer {
    List<String> tokenize(String text);
}
