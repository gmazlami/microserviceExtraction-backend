package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling;

import ch.uzh.ifi.seal.monolith2microservices.graph.MSTGraphClusterer;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.reporting.TextFileReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gmazlami on 11/30/16.
 */
@Service
public class SemanticCouplingDecompositor implements Decompositor {

    private static final Logger logger = LoggerFactory.getLogger(SemanticCouplingDecompositor.class);

    @Autowired
    SemanticCouplingEngine semanticCouplingEngine;

    @Override
    public void decompose(GitRepository repo) {
        try{

            logger.info("Computing semantic couplings...");
            List<SemanticCoupling> couplings = semanticCouplingEngine.computeCouplings(repo);
            logger.info("Successfully computed semantic couplings!");

            logger.info("Decomposing graph into microservices...");
            List<Component> components = MSTGraphClusterer.clusterFromCouplings(couplings);
            logger.info("Successfully computed microservices:");
            components.forEach(c -> {
                logger.info(c.toString());
            });

            TextFileReport.generate(repo, components);

        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
        }

    }
}
