package ch.uzh.ifi.seal.monolith2microservices.graph;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.utils.comparators.ClassNodeComparator;
import ch.uzh.ifi.seal.monolith2microservices.utils.comparators.ComponentComparator;
import ch.uzh.ifi.seal.monolith2microservices.utils.comparators.WeightedEdgeComparator;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.WeightedEdge;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Genc on 08.12.2016.
 */
public final class MSTGraphClusterer {


    private final static ComponentComparator componentComparator = new ComponentComparator();

    private final static ClassNodeComparator classNodeComparator = new ClassNodeComparator();

    private final static WeightedEdgeComparator weightedEdgeComparator = new WeightedEdgeComparator();

    private MSTGraphClusterer(){
        //empty on purpose
    }


    public static Set<Component> clusterWithSplit(List<? extends  BaseCoupling> couplings, int splitThreshold, int numServices){
        List<Component> components =  ConnectedComponents.connectedComponents(computeClusters(MinimumSpanningTree.of(couplings), numServices));

        while(components.size() > 0){

            //Sort components ascending according to size (number of nodes)
            components.sort(componentComparator);

            //Reverse collection to get largest component
            Collections.reverse(components);

            Component largest = components.get(0);


            // split largest component if it exceeds size/degree parameter
            if(largest.getSize() > splitThreshold){
                components.remove(0);
                List<Component> split = splitByDegree(largest);
                components.addAll(split);
            }else{
                return new HashSet<>(components);
            }

        }

        return new HashSet<>(components);
    }

    private static List<Component> splitByDegree(Component component){
        List<ClassNode> nodes = component.getNodes();
        nodes.sort(classNodeComparator);
        Collections.reverse(nodes);

        ClassNode nodeToRemove = nodes.get(0);
        nodes.remove(0);

        nodes.forEach(node -> {
            node.deleteNeighborWithId(nodeToRemove.getId());
        });

        List<Component> connectedComponents = ConnectedComponents.connectedComponentsFromNodes(nodes);
        return connectedComponents.stream().filter(c -> c.getSize() > 1).collect(Collectors.toList());
    }

    private static List<WeightedEdge> computeClusters(Set<WeightedEdge> edges, int numServices){
        List<WeightedEdge> edgeList = edges.stream().collect(Collectors.toList());
        List<WeightedEdge> oldList = null;

        //Sort ascending in order of distances between the files
        Collections.sort(edgeList,weightedEdgeComparator);

        //Reverse collection so that largest distances are first
        Collections.reverse(edgeList);

        int numConnectedComponents = 1;
        int lastNumConnectedComponents = 1;
        int wantedNumComponents = numServices;

        do {
            oldList = new ArrayList<>(edgeList);

            //delete edge with largest distance
            edgeList.remove(0);

            //compute number of connected components by DFS
            numConnectedComponents = ConnectedComponents.numberOfComponents(edgeList);

            //stop if we cannot further improve the decomposition anymore and return last acceptable decomposition
            if(lastNumConnectedComponents > numConnectedComponents){
                return oldList;
            }else{
                lastNumConnectedComponents = numConnectedComponents;
            }

        }while ((numConnectedComponents < wantedNumComponents) && (!edgeList.isEmpty()));

        return edgeList;
    }

}
