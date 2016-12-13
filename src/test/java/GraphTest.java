import ch.uzh.ifi.seal.monolith2microservices.main.utils.WeightedEdgeComparator;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.services.graph.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by Genc on 13.12.2016.
 */
public class GraphTest {


    private List<BaseCoupling> couplings;

    @Before
    public void setUpCouplings(){
        couplings = new ArrayList<>();
        couplings.add(new SemanticCoupling("a","d",7d));
        couplings.add(new SemanticCoupling("d","b",8d));
        couplings.add(new SemanticCoupling("b","g",2d));
        couplings.add(new SemanticCoupling("a","b",2d));
        couplings.add(new SemanticCoupling("d","g",4d));
        couplings.add(new SemanticCoupling("b","e",3d));
        couplings.add(new SemanticCoupling("e","c",8d));
        couplings.add(new SemanticCoupling("c","h",7d));
        couplings.add(new SemanticCoupling("e","g",5d));
        couplings.add(new SemanticCoupling("h","g",4d));
        couplings.add(new SemanticCoupling("g","f",5d));
        couplings.add(new SemanticCoupling("g","j",2d));
        couplings.add(new SemanticCoupling("f","i",7d));
        couplings.add(new SemanticCoupling("j","i",3d));
        couplings.add(new SemanticCoupling("h","k",2d));
        couplings.add(new SemanticCoupling("k","j",9d));
        couplings.add(new SemanticCoupling("j","n",8d));
        couplings.add(new SemanticCoupling("j","m",1d));
        couplings.add(new SemanticCoupling("j","l",4d));
        couplings.add(new SemanticCoupling("l","m",2d));
    }

    @Test
    public void testMinimalSpanningTree(){
        Set<WeightedEdge> edges =  MinimumSpanningTree.of(couplings);
        assertEquals(edges.size(), 13);

        double minimalSpanningTreeWeight = edges.stream().map(edge -> {
            return edge.getScore();
        }).mapToDouble(Double::doubleValue).sum();
        assertEquals(minimalSpanningTreeWeight,2.65, 0.1d );
    }

    @Test
    public void testConnectedComponents(){
        Set<WeightedEdge> edges =  MinimumSpanningTree.of(couplings);
        List<WeightedEdge> edgeList = edges.stream().collect(Collectors.toList());

        assertEquals(1,ConnectedComponents.numberOfComponents(edgeList));

        Collections.sort(edgeList,new WeightedEdgeComparator());
        Collections.reverse(edgeList);
        edgeList.remove(0);

        assertEquals(1, ConnectedComponents.numberOfComponents(edgeList));

        edgeList.remove(0);

        assertEquals(2, ConnectedComponents.numberOfComponents(edgeList));
    }

    @Test
    public void testClustering(){

        List<WeightedEdge> clusters = MSTGraphClusterer.cluster(MinimumSpanningTree.of(couplings));
    }
}
