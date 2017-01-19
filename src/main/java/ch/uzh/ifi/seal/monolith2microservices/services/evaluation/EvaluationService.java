package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.MicroserviceMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.persistence.DecompositionMetricsRepository;
import ch.uzh.ifi.seal.monolith2microservices.persistence.MicroserviceMetricsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genc on 15.01.2017.
 */
@Service
public class EvaluationService {

    private Logger logger = LoggerFactory.getLogger(EvaluationService.class);

    @Autowired
    DecompositionEvaluationService decompositionEvaluationService;

    @Autowired
    MicroserviceEvaluationService microserviceEvaluationService;

    @Autowired
    MicroserviceMetricsRepository microserviceMetricsRepository;

    @Autowired
    DecompositionMetricsRepository decompositionMetricsRepository;


    @Async
    public void performEvaluation(Decomposition decomposition){
        try{
            List<MicroserviceMetrics> microserviceMetrics = computeMicroserviceMetrics(decomposition);
            microserviceMetricsRepository.save(microserviceMetrics);

            EvaluationMetrics metrics = decompositionEvaluationService.computeMetrics(decomposition, microserviceMetrics);
            decompositionMetricsRepository.save(metrics);
        }catch (IOException ioe){
            logger.error(ioe.getMessage());
        }
    }


    private List<MicroserviceMetrics> computeMicroserviceMetrics(Decomposition decomposition) throws IOException{
        List<MicroserviceMetrics> microserviceMetrics = new ArrayList<>();
        for(Component microservice: decomposition.getServices()){
            microserviceMetrics.add(microserviceEvaluationService.from(microservice, decomposition.getRepository()));
        }
        return microserviceMetrics;
    }

}
