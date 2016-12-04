package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling;

import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.tfidf.NgramTfIdf;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by gmazlami on 12/4/16.
 */
public class Similarity {

    public static void computeSimilarity(List<String> doc1, List<String> doc2){
        //combine terms of both documents

        String firstDoc = String.join(" ", doc1);
        String secondDoc = String.join(" ", doc2);
        List<String> documentList = new ArrayList<>();
        documentList.add(firstDoc);
        documentList.add(secondDoc);
        List<Collection<String>> documents = Lists.newArrayList(NgramTfIdf.ngramDocumentTerms(Lists.newArrayList(1, 3),documentList));




    }

}
