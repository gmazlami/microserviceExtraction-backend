package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.Percentile;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gmazlami on 12/5/16.
 */
@Service
public class SemanticCouplingToNodeMapper {

    public List<ClassNode> mapToGraph(List<SemanticCoupling> couplings){

        //double lowerBound = Percentile.fromSemanticCouplings(couplings).getDouble(0.9f);

        double lowerBound = 0.5d;

        Map<String,ClassNode> nodeMap = new HashMap<>();

        for(SemanticCoupling coupling: couplings){
            //System.out.println("Similarity: " + coupling.getSimilarity());
            if(coupling.getSimilarity() > lowerBound){
                String firstFileName = coupling.getFirstClassFileName();
                String secondFileName = coupling.getSecondClassFileName();

                double score = coupling.getSimilarity();

                ClassNode firstNode, secondNode;

                if((firstNode = nodeMap.get(firstFileName)) == null){
                    //create node for first file in pair
                    firstNode = new ClassNode(firstFileName);

                }
                if ((secondNode = nodeMap.get(secondFileName)) == null){
                    //create node for the second file in the pair
                    secondNode = new ClassNode(secondFileName);
                }

                //link both nodes together as new neighbors with their score
                firstNode.addNeighborWithWeight(secondNode,score);
                secondNode.addNeighborWithWeight(firstNode,score);

                nodeMap.put(firstFileName, firstNode);
                nodeMap.put(secondFileName, secondNode);
            }

        }

        return nodeMap.values().stream().collect(Collectors.toList());
    }
}
