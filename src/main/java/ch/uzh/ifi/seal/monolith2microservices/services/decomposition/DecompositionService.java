package ch.uzh.ifi.seal.monolith2microservices.services.decomposition;

import ch.uzh.ifi.seal.monolith2microservices.models.DecompositionParameters;
import ch.uzh.ifi.seal.monolith2microservices.graph.LinearGraphCombination;
import ch.uzh.ifi.seal.monolith2microservices.graph.MSTGraphClusterer;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.persistence.ClassNodeRepository;
import ch.uzh.ifi.seal.monolith2microservices.persistence.ComponentRepository;
import ch.uzh.ifi.seal.monolith2microservices.persistence.DecompositionRepository;
import ch.uzh.ifi.seal.monolith2microservices.persistence.ParametersRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors.ContributorCouplingEngine;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.LogicalCouplingEngine;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.SemanticCouplingEngine;
import ch.uzh.ifi.seal.monolith2microservices.services.evaluation.MicroserviceEvaluationService;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;
import ch.uzh.ifi.seal.monolith2microservices.services.reporting.TextFileReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gmazlami on 12/15/16.
 */
@Service
public class DecompositionService {

    private static final Logger logger = LoggerFactory.getLogger(DecompositionService.class);

    @Autowired
    ClassNodeRepository classNodeRepository;

    @Autowired
    ComponentRepository componentRepository;

    @Autowired
    DecompositionRepository decompositionRepository;

    @Autowired
    ParametersRepository parametersRepository;

    @Autowired
    HistoryService historyService;

    @Autowired
    SemanticCouplingEngine semanticCouplingEngine;

    @Autowired
    LogicalCouplingEngine logicalCouplingEngine;

    @Autowired
    ContributorCouplingEngine contributorCouplingEngine;

    @Autowired
    MicroserviceEvaluationService microserviceEvaluationService;

    public Decomposition decompose(GitRepository repository, DecompositionParameters parameters){

        try {

            logger.info("DECOMPOSITION-------------------------");
            logger.info("STRATEGIES: Logical Coupling: " + parameters.isLogicalCoupling() + " Semantic Coupling: " + parameters.isSemanticCoupling() + "  Contributor Coupling: " + parameters.isContributorCoupling());
            logger.info("PARAMETERS: History Interval Size (s): " + parameters.getIntervalSeconds() + " Target Number of Services: " + parameters.getNumServices());

            List<BaseCoupling> couplings = new ArrayList<>();

            if (parameters.isLogicalCoupling() && parameters.isSemanticCoupling() && parameters.isContributorCoupling()) {

                couplings = LinearGraphCombination.create().withLogicalCouplings(computeLogicalCouplings(repository, parameters))
                        .withSemanticCouplings(computeSemanticCouplings(repository))
                        .withContributorCouplings(computeContributorCouplings(repository)).generate();

            } else if (parameters.isLogicalCoupling() && parameters.isSemanticCoupling()) {

                couplings = LinearGraphCombination.create().withLogicalCouplings(computeLogicalCouplings(repository, parameters))
                        .withSemanticCouplings(computeSemanticCouplings(repository)).generate();

            } else if (parameters.isLogicalCoupling() && parameters.isContributorCoupling()) {

                couplings = LinearGraphCombination.create().withLogicalCouplings(computeLogicalCouplings(repository, parameters))
                        .withContributorCouplings(computeContributorCouplings(repository)).generate();

            } else if (parameters.isContributorCoupling() && parameters.isSemanticCoupling()) {

                couplings = LinearGraphCombination.create().withContributorCouplings(computeContributorCouplings(repository))
                        .withSemanticCouplings(computeSemanticCouplings(repository)).generate();

            } else if (parameters.isLogicalCoupling()) {

                couplings = LinearGraphCombination.create().withLogicalCouplings(computeLogicalCouplings(repository, parameters)).generate();

            } else if (parameters.isSemanticCoupling()) {

                couplings = LinearGraphCombination.create().withSemanticCouplings(computeSemanticCouplings(repository)).generate();

            } else if (parameters.isContributorCoupling()) {
                couplings = LinearGraphCombination.create().withContributorCouplings(computeContributorCouplings(repository)).generate();
            }

            Set<Component> components = MSTGraphClusterer.clusterWithSplit(couplings, parameters.getSizeThreshold(), parameters.getNumServices());

            logger.info("Saving decomposition to database.");

            components.forEach(c -> {
                c.getNodes().forEach(n -> {
                    classNodeRepository.save(n);
                });
                componentRepository.save(c);
                logger.info(c.toString());
            });

            parametersRepository.save(parameters);

            Decomposition decomposition = new Decomposition();
            decomposition.setComponents(components);
            decomposition.setRepository(repository);
            decomposition.setParameters(parameters);
            decompositionRepository.save(decomposition);

            logger.info("Saved all decomposition info and components to database!");

            TextFileReport.generate(repository, components);

            return decomposition;

        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            Decomposition emptyDecomposition = new Decomposition();
            emptyDecomposition.setComponents(new HashSet<>());
            emptyDecomposition.setRepository(repository);
            return emptyDecomposition;
        }
    }

    private List<ContributorCoupling> computeContributorCouplings(GitRepository repository) throws Exception{
        return contributorCouplingEngine.computeCouplings(historyService.computeChangeEvents(repository));
    }

    private List<SemanticCoupling> computeSemanticCouplings(GitRepository repository) throws IOException{
        return semanticCouplingEngine.computeCouplings(repository);
    }

    private List<LogicalCoupling> computeLogicalCouplings(GitRepository repository, DecompositionParameters parameters) throws Exception{
        List<ChangeEvent> history = historyService.computeChangeEvents(repository);
        List<ChangeEvent> correctedHistory = historyService.cleanHistory(history);
        return logicalCouplingEngine.computeCouplings(correctedHistory, parameters.getIntervalSeconds());
    }

}
