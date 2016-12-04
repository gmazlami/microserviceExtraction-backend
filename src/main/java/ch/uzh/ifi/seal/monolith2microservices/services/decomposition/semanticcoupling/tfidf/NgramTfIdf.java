package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf;

import org.apache.commons.cli.*;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Utility to calculate tf-idf for text n-grams
 */
public class NgramTfIdf {
    /**
     * Tokenize a set of documents and extract n-gram terms
     *
     * @param tokenizer document tokenizer
     * @param ns        n-gram orders
     * @param documents set of documents from which to extract terms
     * @return iterator over document terms, where each document's terms is an iterator over strings
     */
    public static Iterable<Collection<String>> ngramDocumentTerms(Tokenizer tokenizer, List<Integer> ns,
                                                                  Iterable<String> documents) {
        // Tokenize the documents.
        List<List<String>> tokenizedDocuments = new ArrayList<>();
        for (String document : documents) {
            List<String> tokens = tokenizer.tokenize(document);
            tokenizedDocuments.add(tokens);
        }
        // Extract N-grams as the terms in our model.
        List<Collection<String>> documentTerms = new ArrayList<>();
        for (List<String> tokenizedDocument : tokenizedDocuments) {
            Collection<String> terms = new ArrayList<>();
            for (int n : ns) {
                for (List<String> ngram : ngrams(n, tokenizedDocument)) {
                    String term = StringUtils.join(ngram, " ");
                    terms.add(term);
                }
            }
            documentTerms.add(terms);
        }
        return documentTerms;
    }

    /**
     * Tokenize a set of documents as alphanumeric words and extract n-gram terms
     *
     * @param ns        n-gram orders
     * @param documents set of documents from which to extract terms
     * @return iterator over document terms, where each document's terms is an iterator over strings
     */
    public static Iterable<Collection<String>> ngramDocumentTerms(List<Integer> ns, Iterable<String> documents) {
        return ngramDocumentTerms(new RegularExpressionTokenizer(), ns, documents);
    }

    private static List<List<String>> ngrams(int n, List<String> tokens) {
        List<List<String>> ngrams = new ArrayList<>();
        for (int i = 0; i < tokens.size() - n + 1; i++) {
            ngrams.add(tokens.subList(i, i + n));
        }
        return ngrams;
    }

    private static String termStatistics(Map<String, Double> stats) {
        // Print terms in decreasing numerical order
        List<Map.Entry<String, Double>> es = new ArrayList<>(stats.entrySet());
        Collections.sort(es, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, Double> e : es) {
            fields.add(String.format("%s = %6f", e.getKey(), e.getValue()));
        }
        return StringUtils.join(fields, "\t");
    }


}
