package ch.uzh.ifi.seal.monolith2microservices.services.graph;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;


/**
 * Created by gmazlami on 12/8/16.
 */
@SuppressWarnings("unchecked")
public class MinimumSpanningTree {

    public static Set<WeightedEdge> of(List<BaseCoupling> couplings){
        return new MinimumSpanningTree().computeMST(couplings);
    }


    private MinimumSpanningTree(){
        super();
    }


    private Set<WeightedEdge> computeMST(List<BaseCoupling> couplings){
        KruskalMinimumSpanningTree<String, WeightedEdge> mst = new KruskalMinimumSpanningTree<>(createGraph(couplings));
        return mst.getMinimumSpanningTreeEdgeSet();
    }

    private SimpleWeightedGraph createGraph(List<BaseCoupling> couplings){
        SimpleWeightedGraph<String, WeightedEdge> graph = new SimpleWeightedGraph<>(WeightedEdge.class);

        couplings.forEach(coupling -> {
            graph.addVertex(coupling.getFirstFileName());
            graph.addVertex(coupling.getSecondFileName());

            WeightedEdge currentEdge = new WeightedEdge();
            currentEdge.setScore(1/coupling.getScore());
            graph.addEdge(coupling.getFirstFileName(), coupling.getSecondFileName(),currentEdge);

            //Add the score inversed (1/score) so that high score means close distance between vertices
            graph.setEdgeWeight(currentEdge, (1/coupling.getScore()));
        });

        return graph;
    }



}
