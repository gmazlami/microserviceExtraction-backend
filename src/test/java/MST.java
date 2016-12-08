import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Edge;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;


/**
 * Created by gmazlami on 12/8/16.
 */
public class MST {

    @Test
    public void testGraph(){
        SimpleWeightedGraph<String, DefaultWeightedEdge> myGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        DefaultWeightedEdge edge = new DefaultWeightedEdge();
        ClassNode test1 = new ClassNode("test1");
        ClassNode test2 = new ClassNode("test2");

        myGraph.addVertex("test1");
        myGraph.addVertex("test2");

        DefaultWeightedEdge e1 = myGraph.addEdge("test1","test2");
        myGraph.setEdgeWeight(e1, 5);
        System.out.println(myGraph.edgeSet());
    }
}
