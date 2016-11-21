package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gmazlami on 11/21/16.
 */
public class ContributorCouplingToNodeMapper {

    //TODO: Include filtering of low values by percentile before creating graph

    public List<ClassNode> mapToGraph(List<ContributorCoupling> couplings){

        Map<String,ClassNode> nodeMap = new HashMap<>();

        for(ContributorCoupling coupling: couplings){
            String firstFileName = coupling.getFirstFileName();
            String secondFileName = coupling.getSecondFileName();

            int score = coupling.getScore();

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

        return nodeMap.values().stream().collect(Collectors.toList());
    }




}
