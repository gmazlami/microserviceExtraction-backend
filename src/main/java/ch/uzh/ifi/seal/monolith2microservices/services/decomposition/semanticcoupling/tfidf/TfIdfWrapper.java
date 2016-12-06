package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf;

import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf.NgramTfIdf;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf.TfIdf;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by gmazlami on 12/4/16.
 */
public class TfIdfWrapper {

    public static double computeSimilarity(List<String> doc1, List<String> doc2){

        //combined term list
        List<String> terms = terms(doc1, doc2);

        //construct ngram documents for TF-IDF computation
        List<Collection<String>> documents = ngramDocuments(doc1,doc2);

        //compute TF
        List<Map<String, Double>> tfs = Lists.newArrayList(TfIdf.tfs(documents));

        //compute IDF
        Map<String, Double> idf = TfIdf.idfFromTfs(tfs);

        //compute vector for doc1
        List<Double> vector1 = vector(idf, terms, tfs.get(0));

        //compute vector for doc2
        List<Double> vector2 = vector(idf, terms, tfs.get(1));

        //compute similarity
        return cosine(vector1,vector2);
    }

    private static List<Double> vector(Map<String, Double> idf, List<String> terms, Map<String, Double> tf){

        List<Double> vector = new ArrayList<>();
        Map<String, Double> tfIdf = TfIdf.tfIdf(tf, idf);

        for(String term: terms){
            vector.add(tfIdf.get(term));
        }
        return vector;
    }

    private static List<String> terms(List<String> doc1, List<String> doc2){
        List<String> terms = new ArrayList<>();
        terms.addAll(doc1);
        terms.addAll(doc2);
        return terms;
    }

    private static List<Collection<String>> ngramDocuments(List<String> doc1, List<String> doc2){
        String firstDoc = String.join(" ", doc1);
        String secondDoc = String.join(" ", doc2);
        List<String> documentList = new ArrayList<>();
        documentList.add(firstDoc);
        documentList.add(secondDoc);
        return Lists.newArrayList(NgramTfIdf.ngramDocumentTerms(Lists.newArrayList(1, 3),documentList));
    }

    private static double cosine(List<Double> vector1, List<Double> vector2){

        double numerator = 0.0d;
        for(int i=0; i < vector1.size(); i++){
            numerator += (vector1.get(i) * vector2.get(i));
        }

        double factor1 = 0.0d;
        for(double element : vector1){
            factor1 += Math.pow(element,2);
        }

        double factor2 = 0.0d;
        for(double element : vector2){
            factor2 += Math.pow(element,2);
        }

        return numerator / (Math.sqrt(factor1) * Math.sqrt(factor2));
    }
}
