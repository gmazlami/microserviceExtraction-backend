package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors;

import ch.uzh.ifi.seal.monolith2microservices.graph.MSTGraphClusterer;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class ContributorCouplingDecompositor implements Decompositor {

    private static final Logger logger = LoggerFactory.getLogger(ContributorCouplingDecompositor.class);

    @Autowired
    HistoryService historyService;

    @Autowired
    ContributorCouplingEngine couplingEngine;

    @Override
    public void decompose(GitRepository repo) {
        try{
            logger.info("Computing history...");
            List<ChangeEvent> changeHistory = historyService.computeChangeEvents(repo);
            logger.info("Successfully computed history!");

            logger.info("Computing contributor couplings...");
            List<ContributorCoupling> couplings = couplingEngine.computeCouplings(changeHistory);
            logger.info("Successfully computed contributor couplings!");

            logger.info("Decomposing graph into microservices...");
            List<ch.uzh.ifi.seal.monolith2microservices.models.graph.Component> components = MSTGraphClusterer.clusterFromCouplings(couplings);
            logger.info("Successfully computed microservices:");
            components.forEach(c -> {
                logger.info(c.toString());
            });

        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
        }

    }

}
