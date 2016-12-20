package ch.uzh.ifi.seal.monolith2microservices.conversion;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.util.*;

/**
 * Created by gmazlami on 12/20/16.
 */
public class GraphRepresentationConverter {

    public static Set<EdgeRepresentation> convertEdges(Set<Component> components, Set<NodeRepresentation> nodes){
        Map<String,?> nodeMap = new HashMap<>();
        Set<EdgeRepresentation> edges = new HashSet<>();
        Map<String,Long> idMap = constructIdMap(nodes);

        for(Component c: components){
            for(ClassNode n: c.getNodes()){
                n.getNeighbors().forEach(neighborPair -> {
                    String key = getSortedIdString(n.getId(), neighborPair.getNodeId());
                    if(nodeMap.get(key)==null){
                        edges.add(new EdgeRepresentation(idMap.get(n.getId()), idMap.get(neighborPair.getNodeId())));
                    }

                });
            }
        }
        return edges;
    }


    public static Set<NodeRepresentation> convertNodes(Set<Component> components){
        Set<NodeRepresentation> nodes = new HashSet<>();
        long counter = 1;

        for(Component c: components){
            for (ClassNode n: c.getNodes()){
                nodes.add(new NodeRepresentation(counter,n.getId()));
                counter++;
            }
        }
        return nodes;
    }

    private static Map<String,Long> constructIdMap(Set<NodeRepresentation> nodes){
        Map<String,Long> idMap = new HashMap<>();
        nodes.forEach(n -> {
            idMap.put(n.getLabel(), n.getId());
        });
        return idMap;
    }

    private static String getSortedIdString(String firstId, String secondId){
        List<String> strings = Arrays.asList(new String[] {firstId, secondId});
        Collections.sort(strings);
        return strings.stream().reduce("", String::concat);

    }

}
