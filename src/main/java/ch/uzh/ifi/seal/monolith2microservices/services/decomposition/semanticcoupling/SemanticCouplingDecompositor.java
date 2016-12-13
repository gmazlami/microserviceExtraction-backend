package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.GraphToMicroserviceMapper;
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

    @Autowired
    SemanticCouplingToNodeMapper nodeMapper;

    @Autowired
    GraphToMicroserviceMapper graphToMicroserviceMapper;

    @Override
    public void decompose(GitRepository repo) {
        try{



//            logger.info("Computing semantic couplings...");
//            List<SemanticCoupling> couplings = semanticCouplingEngine.computeCouplings(repo);
//            logger.info("Successfully computed semantic couplings!");
//
//            logger.info("Computing nodes...");
//            List<ClassNode> nodes = nodeMapper.mapToGraph(couplings);
//            logger.info("Successfully computed nodes!");
//
//            logger.info("Computing microservices...");
//            List<Microservice> microservices = graphToMicroserviceMapper.mapToMicroservices(nodes);
//            logger.info("Computed the following microservices: ");
//            microservices.forEach(m -> logger.info(m.toString()));
//
//            logger.info("Generating text report...");
//            TextFileReport.generate(repo, microservices);
//            logger.info("Finished.");

        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
        }

    }
}
