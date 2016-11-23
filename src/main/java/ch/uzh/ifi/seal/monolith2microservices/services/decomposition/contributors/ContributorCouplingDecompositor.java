package java.ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Microservice;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.GraphToMicroserviceMapper;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors.ContributorCouplingEngine;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors.ContributorCouplingToNodeMapper;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;
import ch.uzh.ifi.seal.monolith2microservices.services.reporting.TextFileReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContributorCouplingDecompositor implements Decompositor {

    private static final Logger logger = LoggerFactory.getLogger(ContributorCouplingDecompositor.class);

    @Autowired
    HistoryService historyService;

    @Autowired
    ContributorCouplingEngine couplingEngine;

    @Autowired
    ContributorCouplingToNodeMapper nodeMapper;

    @Autowired
    GraphToMicroserviceMapper graphToMicroserviceMapper;

    @Override
    public void decompose(GitRepository repo) {
        try{
            logger.info("Computing history...");
            List<ChangeEvent> changeHistory = historyService.computeChangeEvents(repo);
            logger.info("Successfully computed history!");

            logger.info("Computing logical couplings...");
            List<ContributorCoupling> couplings = couplingEngine.computeCouplings(changeHistory);
            logger.info("Successfully computed logical couplings!");

            logger.info("Computing nodes...");
            List<ClassNode> nodes = nodeMapper.mapToGraph(couplings);
            logger.info("Successfully computed nodes!");

            logger.info("Computing microservices...");
            List<Microservice> microservices = graphToMicroserviceMapper.mapToMicroservices(nodes);
            logger.info("Computed the following microservices: ");

            microservices.forEach(m -> logger.info(m.toString()));

            logger.info("Generating text report...");
            TextFileReport.generate(repo, microservices);
            logger.info("Finished.");

        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
        }

    }

}
