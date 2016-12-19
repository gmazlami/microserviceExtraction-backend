import ch.uzh.ifi.seal.monolith2microservices.graph.LinearGraphCombination;
import ch.uzh.ifi.seal.monolith2microservices.graph.MSTGraphClusterer;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by gmazlami on 12/15/16.
 */
public class CouplingTest {

    @Test
    public void testCombinedCouplings(){
        //Test Data
        List<SemanticCoupling> semanticCouplings = generateGenericSemanticCouplings();
        List<LogicalCoupling> logicalCouplings = generateGenericLogicalCouplings();
        List<ContributorCoupling> contributorCouplings = generateGenericContributorCouplings();

        //Expected Data
        List<BaseCoupling> expectedCouplings = generateExpectedCombinedCouplings();

        //Compute result
        List<BaseCoupling> combinedCouplings = LinearGraphCombination.create().withContributorCouplings(contributorCouplings).withLogicalCouplings(logicalCouplings).withSemanticCouplings(semanticCouplings).generate();
        assertEquals(expectedCouplings,combinedCouplings);
    }

    @Test
    public void testCombinedCouplingsWithoutLogical(){
        //Test Data
        List<SemanticCoupling> semanticCouplings = generateGenericSemanticCouplings();
        List<ContributorCoupling> contributorCouplings = generateGenericContributorCouplings();

        //Expected Data
        List<BaseCoupling> expectedCouplings = generateExpectedCombinedCouplingsWithoutLogicalCouplings();

        //Compute result
        List<BaseCoupling> combinedCouplings = LinearGraphCombination.create().withContributorCouplings(contributorCouplings).withSemanticCouplings(semanticCouplings).generate();
        assertEquals(expectedCouplings,combinedCouplings);
    }

    @Test
    public void testLogicalCouplingCombination(){
        //Test Data
        List<LogicalCoupling> couplings = generateExtendedLogicalCouplings();

        // Compute result from couplings after combination
        List<BaseCoupling> combinedCouplings = LinearGraphCombination.create().withLogicalCouplings(couplings).generate();
        List<Component> componentsFromCombined = MSTGraphClusterer.clusterFromCouplings(combinedCouplings);

        // Compute result from couplings without prior combination
        List<Component> componentsFromLogicalCoupling = MSTGraphClusterer.clusterFromCouplings(couplings);

        // Resulting components should be the same
        assertEquals(componentsFromCombined, componentsFromLogicalCoupling);

    }

    private List<LogicalCoupling> generateExtendedLogicalCouplings(){
        List<LogicalCoupling> couplings = new ArrayList<>();
        couplings.add(new LogicalCoupling("A","B",5.0));
        couplings.add(new LogicalCoupling("A","C",3.0));
        couplings.add(new LogicalCoupling("C","D",2.0));
        couplings.add(new LogicalCoupling("B","D",7.0));
        couplings.add(new LogicalCoupling("C","E",1.0));
        couplings.add(new LogicalCoupling("E","H",7.0));
        couplings.add(new LogicalCoupling("H","I",8.0));
        couplings.add(new LogicalCoupling("D","G",2.0));
        couplings.add(new LogicalCoupling("G","L",8.0));
        couplings.add(new LogicalCoupling("L","M",7.0));
        couplings.add(new LogicalCoupling("M","G",6.0));
        couplings.add(new LogicalCoupling("B","F",2.0));
        couplings.add(new LogicalCoupling("F","J",9.0));
        couplings.add(new LogicalCoupling("J","K",7.0));
        couplings.add(new LogicalCoupling("K","F",5.0));
        return couplings;
    }

    private List<ContributorCoupling> generateGenericContributorCouplings(){
        List<ContributorCoupling> contributorCouplings = new ArrayList<>();
        contributorCouplings.add(new ContributorCoupling("A","B", 2.0));
        contributorCouplings.add(new ContributorCoupling("A","C", 4.0));
        contributorCouplings.add(new ContributorCoupling("A","E", 3.0));
        return contributorCouplings;
    }

    private List<SemanticCoupling> generateGenericSemanticCouplings(){
        List<SemanticCoupling> semanticCouplings = new ArrayList<>();
        semanticCouplings.add(new SemanticCoupling("A","B",6.0));
        semanticCouplings.add(new SemanticCoupling("B","C",1.0));
        semanticCouplings.add(new SemanticCoupling("C","D",3.0));
        semanticCouplings.add(new SemanticCoupling("D","E",7.0));
        return semanticCouplings;

    }

    private List<LogicalCoupling> generateGenericLogicalCouplings(){
        List<LogicalCoupling> logicalCouplings = new ArrayList<>();
        logicalCouplings.add(new LogicalCoupling("A","B",5.0));
        logicalCouplings.add(new LogicalCoupling("A","C",3.0));
        logicalCouplings.add(new LogicalCoupling("C","D",2.0));
        logicalCouplings.add(new LogicalCoupling("B","D",7.0));
        return logicalCouplings;
    }

    private List<BaseCoupling> generateExpectedCombinedCouplings(){
        List<BaseCoupling> expectedCouplings = new ArrayList<>();
        expectedCouplings.add(new BaseCoupling("A","B", 13d));
        expectedCouplings.add(new BaseCoupling("B","C", 1d));
        expectedCouplings.add(new BaseCoupling("A","C", 7d));
        expectedCouplings.add(new BaseCoupling("C","D", 5d));
        expectedCouplings.add(new BaseCoupling("B","D", 7d));
        expectedCouplings.add(new BaseCoupling("A","E", 3d));
        expectedCouplings.add(new BaseCoupling("D","E", 7d));
        return expectedCouplings;
    }

    private List<BaseCoupling> generateExpectedCombinedCouplingsWithoutLogicalCouplings(){
        List<BaseCoupling> expectedCouplings = new ArrayList<>();
        expectedCouplings.add(new BaseCoupling("A","B",8d));
        expectedCouplings.add(new BaseCoupling("B","C",1d));
        expectedCouplings.add(new BaseCoupling("A","C",4d));
        expectedCouplings.add(new BaseCoupling("C","D",3d));
        expectedCouplings.add(new BaseCoupling("A","E",3d));
        expectedCouplings.add(new BaseCoupling("D","E",7d));
        return expectedCouplings;
    }
}
