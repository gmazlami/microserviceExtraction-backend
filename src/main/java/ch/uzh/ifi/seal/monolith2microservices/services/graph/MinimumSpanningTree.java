package ch.uzh.ifi.seal.monolith2microservices.services.graph;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;


/**
 * Created by gmazlami on 12/8/16.
 */
@SuppressWarnings("unchecked")
public class MinimumSpanningTree {

    public static Set<DefaultWeightedEdge> of(List<BaseCoupling> couplings){
        return new MinimumSpanningTree().computeMST(couplings);
    }


    private MinimumSpanningTree(){
        super();
    }


    private Set<DefaultWeightedEdge> computeMST(List<BaseCoupling> couplings){
        KruskalMinimumSpanningTree<String, DefaultWeightedEdge> kruskal = new KruskalMinimumSpanningTree<>(createGraph(couplings));
        return kruskal.getMinimumSpanningTreeEdgeSet();
    }

    private SimpleWeightedGraph createGraph(List<BaseCoupling> couplings){
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        couplings.forEach(coupling -> {
            graph.addVertex(coupling.getFirstFileName());
            graph.addVertex(coupling.getSecondFileName());
            DefaultWeightedEdge currentEdge = graph.addEdge(coupling.getFirstFileName(),coupling.getSecondFileName());
            graph.setEdgeWeight(currentEdge, coupling.getScore());
        });

        return graph;
    }



}
