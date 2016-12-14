package ch.uzh.ifi.seal.monolith2microservices.graph;


import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.NodeWeightPair;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.WeightedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Genc on 09.12.2016.
 */
public final class ConnectedComponents {


    private ConnectedComponents(){
        //empty on purpose
    }

    public static List<Component> connectedComponents(List<WeightedEdge> edgeList){
        List<ClassNode> nodes = convertEdgeListToNodeList(edgeList);
        return performDfs(nodes);
    }

    public static int numberOfComponents(List<WeightedEdge> edgeList){
        return (int)  connectedComponents(edgeList).stream().filter(singleNodeFilter).count();
    }


    private static Predicate<Component> singleNodeFilter = (component) -> {
        if(component.getNodes().size() < 2){
            return false;
        }else{
            return true;
        }
    };

    private static List<ClassNode> convertEdgeListToNodeList(List<WeightedEdge> edgeList){

        Map<String,ClassNode> nodeMap = new HashMap<>();

        for(WeightedEdge edge: edgeList){

            String firstFileName = edge.getFirstFileName();
            String secondFileName = edge.getSecondFileName();

            double score = edge.getScore();

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

    private static List<Component> performDfs(List<ClassNode> nodes){
        List<Component> components = new ArrayList<>();
        for(ClassNode node : nodes){
            if(!node.isVisited()){
                node.setVisited(true);
                Component c = new Component();
                c.setVisited(true);
                c.addNode(node);
                dfs(node,c);
                components.add(c);
            }
        }
        return components;
    }


    private static void dfs(ClassNode node, Component component){
        for(NodeWeightPair neighbor : node.getNeighbors()){
            if(!neighbor.getNode().isVisited()){
                neighbor.getNode().setVisited(true);
                component.addNode(neighbor.getNode());
                dfs(neighbor.getNode(), component);
            }
        }
    }

}
