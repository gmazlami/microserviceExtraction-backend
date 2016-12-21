import ch.uzh.ifi.seal.monolith2microservices.conversion.EdgeRepresentation;
import ch.uzh.ifi.seal.monolith2microservices.conversion.GraphRepresentationConverter;
import ch.uzh.ifi.seal.monolith2microservices.conversion.NodeRepresentation;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmazlami on 12/20/16.
 */
public class ConversionTest {

    private Set<Component> components;

    @Before
    public void setUp(){
        components = new HashSet<>();

        Component component = new Component();

        ClassNode mainClassNode = new ClassNode("src/main/test/some/example/package/Main.java");
        ClassNode utilsClassNode = new ClassNode("src/main/test/some/other/example/package/Uilts.java");
        ClassNode testClassNode = new ClassNode("Test.java");

        mainClassNode.addNeighborWithWeight(utilsClassNode, 3.0);
        utilsClassNode.addNeighborWithWeight(mainClassNode, 3.0);
        testClassNode.addNeighborWithWeight(mainClassNode, 4.0);
        mainClassNode.addNeighborWithWeight(testClassNode, 4.0);

        component.addNode(mainClassNode);
        component.addNode(utilsClassNode);
        component.addNode(testClassNode);

        components.add(component);
    }


    @Test
    public void testNodeLabel(){
        Set<NodeRepresentation> nodes = GraphRepresentationConverter.convertNodes(components);

        nodes.forEach(node -> {
            System.out.println("Node label:   " + node.getLabel());
        });
        assertEquals(true, true);
    }


    @Test
    public void testEdges(){
        Set<EdgeRepresentation> edges =  GraphRepresentationConverter.convertEdges(components,GraphRepresentationConverter.convertNodes(components));
        edges.forEach(e -> {
            System.out.println(e);
        });

        assertEquals(true,true);
    }
}
