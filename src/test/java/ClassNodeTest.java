import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.utils.comparators.ClassNodeComparator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by gmazlami on 12/19/16.
 */
public class ClassNodeTest {

    private List<ClassNode> nodes;

    private ClassNode nodeA, nodeC, nodeG;

    @Before
    public void setUp(){

        //create all nodes
        ClassNode a = new ClassNode("a");
        ClassNode b = new ClassNode("b");
        ClassNode c = new ClassNode("c");
        ClassNode d = new ClassNode("d");
        ClassNode e = new ClassNode("e");
        ClassNode f = new ClassNode("f");
        ClassNode g = new ClassNode("g");

        a.addNeighborWithWeight(b, 7d);
        a.addNeighborWithWeight(d, 6d);
        a.addNeighborWithWeight(c, 5d);
        a.addNeighborWithWeight(g, 7d);

        b.addNeighborWithWeight(a, 7d);

        c.addNeighborWithWeight(a, 5d);
        c.addNeighborWithWeight(g, 2d);
        c.addNeighborWithWeight(f, 3d);
        c.addNeighborWithWeight(e, 2d);

        d.addNeighborWithWeight(a, 6d);

        e.addNeighborWithWeight(g, 5d);
        e.addNeighborWithWeight(c, 2d);

        f.addNeighborWithWeight(c, 3d);

        g.addNeighborWithWeight(a, 7d);
        g.addNeighborWithWeight(c, 2d);
        g.addNeighborWithWeight(e, 5d);

        ClassNode[] nodeArray = {a,b,c,d,e,f,g};
        nodes = Arrays.asList(nodeArray);

        nodeA = a;
        nodeC = c;
        nodeG = g;
    }

    @Test
    public void testCombinedWeight(){
        assertEquals(25d, nodeA.getCombinedWeight(), 0.1);
        assertEquals(12d, nodeC.getCombinedWeight(), 0.1);
        assertEquals(14d, nodeG.getCombinedWeight(), 0.1);
    }

    @Test
    public void testDegree(){
        assertEquals(4, nodeA.getDegree());
        assertEquals(4, nodeC.getDegree());
        assertEquals(3, nodeG.getDegree());
    }

    @Test
    public void testComparator(){
        nodes.sort(new ClassNodeComparator());
        Collections.reverse(nodes);

        assertEquals(nodes.get(0), nodeA);
        assertEquals(nodes.get(1), nodeC);
        assertEquals(nodes.get(2), nodeG);

    }
}
