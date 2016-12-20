import ch.uzh.ifi.seal.monolith2microservices.graph.MSTGraphClusterer;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gmazlami on 12/19/16.
 */
public class ClusteringTest {

    @Test
    public void testDecompositionWithLargeComponentSplit(){
        // Test Data
        List<BaseCoupling> originalGraph = generateTestGraph();

        // Expected outcome
        Set<Component> expectedComponents = generateExpectedComponents();

        // Compute result
        Set<Component> computedComponents = MSTGraphClusterer.clusterWithSplit(originalGraph, 5, 4);

        // compare results, should be the same
        assertEquals(expectedComponents, computedComponents);
    }


    private Set<Component> generateExpectedComponents(){
        Set<Component> components = new HashSet<>();

        Component component = new Component();
        component.addNode(new ClassNode("E"));
        component.addNode(new ClassNode("F"));
        components.add(component);

        component = new Component();
        component.addNode(new ClassNode("C"));
        component.addNode(new ClassNode("D"));
        components.add(component);

        component = new Component();
        component.addNode(new ClassNode("I"));
        component.addNode(new ClassNode("J"));
        components.add(component);

        component = new Component();
        component.addNode(new ClassNode("K"));
        component.addNode(new ClassNode("L"));
        components.add(component);

        component = new Component();
        component.addNode(new ClassNode("M"));
        component.addNode(new ClassNode("N"));
        component.addNode(new ClassNode("O"));
        component.addNode(new ClassNode("P"));
        components.add(component);

        component = new Component();
        component.addNode(new ClassNode("Q"));
        component.addNode(new ClassNode("R"));
        component.addNode(new ClassNode("S"));
        component.addNode(new ClassNode("T"));
        components.add(component);

        return components;
    }

    private List<BaseCoupling> generateTestGraph(){
        List<BaseCoupling> graph = new ArrayList<>();
        graph.add(new BaseCoupling("A","B",7));
        graph.add(new BaseCoupling("B","C",9));
        graph.add(new BaseCoupling("C","D",6));
        graph.add(new BaseCoupling("B","E",8));
        graph.add(new BaseCoupling("E","F",7));
        graph.add(new BaseCoupling("F","G",1));
        graph.add(new BaseCoupling("G","H",7));
        graph.add(new BaseCoupling("H","I",8));
        graph.add(new BaseCoupling("I","J",7));
        graph.add(new BaseCoupling("H","K",9));
        graph.add(new BaseCoupling("K","L",6));
        graph.add(new BaseCoupling("G","M",1));
        graph.add(new BaseCoupling("M","N",7));
        graph.add(new BaseCoupling("N","O",5));
        graph.add(new BaseCoupling("O","P",8));
        graph.add(new BaseCoupling("M","Q",1));
        graph.add(new BaseCoupling("Q","R",8));
        graph.add(new BaseCoupling("R","S",7));
        graph.add(new BaseCoupling("S","T",8));
        return graph;
    }
}
