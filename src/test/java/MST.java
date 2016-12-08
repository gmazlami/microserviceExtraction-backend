import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by gmazlami on 12/8/16.
 */
public class MST {

    @Test
    public void testGraph(){
        SimpleWeightedGraph<String, DefaultWeightedEdge> myGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        myGraph.addVertex("a");
        myGraph.addVertex("b");
        myGraph.addVertex("c");
        myGraph.addVertex("d");
        myGraph.addVertex("e");
        myGraph.addVertex("f");
        myGraph.addVertex("g");
        myGraph.addVertex("h");

        DefaultWeightedEdge e1 = myGraph.addEdge("a","b");
        myGraph.setEdgeWeight(e1, 5);

        DefaultWeightedEdge e2 = myGraph.addEdge("a","c");
        myGraph.setEdgeWeight(e2, 2);

        DefaultWeightedEdge e3 = myGraph.addEdge("b","d");
        myGraph.setEdgeWeight(e3, 7);

        DefaultWeightedEdge e4 = myGraph.addEdge("c","d");
        myGraph.setEdgeWeight(e4, 5);

        DefaultWeightedEdge e5 = myGraph.addEdge("b","e");
        myGraph.setEdgeWeight(e5, 11);

        DefaultWeightedEdge e6 = myGraph.addEdge("e","f");
        myGraph.setEdgeWeight(e6, 1);

        DefaultWeightedEdge e7 = myGraph.addEdge("f","g");
        myGraph.setEdgeWeight(e7, 3);

        DefaultWeightedEdge e8 = myGraph.addEdge("g","e");
        myGraph.setEdgeWeight(e8, 5);

        DefaultWeightedEdge e9 = myGraph.addEdge("g","h");
        myGraph.setEdgeWeight(e9, 9);

        DefaultWeightedEdge e10 = myGraph.addEdge("h","c");
        myGraph.setEdgeWeight(e10, 4);

        DefaultWeightedEdge e11 = myGraph.addEdge("d","g");
        myGraph.setEdgeWeight(e11, 10);

        KruskalMinimumSpanningTree<String, DefaultWeightedEdge> minimumSpanningTree = new KruskalMinimumSpanningTree<>(myGraph);

        assert(minimumSpanningTree.getMinimumSpanningTreeEdgeSet().size() == 7d);
        assert(minimumSpanningTree.getMinimumSpanningTreeTotalWeight() == 29d);
    }
}
