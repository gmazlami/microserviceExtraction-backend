package ch.uzh.ifi.seal.monolith2microservices.services.graph;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.WeightedEdgeComparator;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Microservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Genc on 08.12.2016.
 */
public class MSTGraphClusterer {

    private MSTGraphClusterer(){
        super();
    }

    public static List<WeightedEdge> cluster(Set<WeightedEdge> edges){
        return new MSTGraphClusterer().computeClusters(edges);
    }



    private List<WeightedEdge> computeClusters(Set<WeightedEdge> edges){
        List<WeightedEdge> edgeList = edges.stream().collect(Collectors.toList());
        List<WeightedEdge> oldList = null;

        //Sort ascending in order of distances between the files
        Collections.sort(edgeList,new WeightedEdgeComparator());

        //Reverse collection so that largest distances are first
        Collections.reverse(edgeList);

        int numConnectedComponents = 1;
        int lastNumConnectedComponents = 1;
        int wantedNumComponents = 3;

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
