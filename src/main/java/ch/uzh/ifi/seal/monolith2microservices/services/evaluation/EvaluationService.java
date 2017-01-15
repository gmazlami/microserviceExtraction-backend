package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.MicroserviceMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genc on 15.01.2017.
 */
@Service
public class EvaluationService {

    @Autowired
    DecompositionEvaluationService decompositionEvaluationService;

    @Autowired
    MicroserviceEvaluationService microserviceEvaluationService;




    public void performEvaluation(Decomposition decomposition) throws IOException{
        List<MicroserviceMetrics> microserviceMetrics = computeMicroserviceMetrics(decomposition);




    }




    private List<MicroserviceMetrics> computeMicroserviceMetrics(Decomposition decomposition) throws IOException{
        List<MicroserviceMetrics> microserviceMetrics = new ArrayList<>();
        for(Component microservice: decomposition.getServices()){
            microserviceMetrics.add(microserviceEvaluationService.from(microservice, decomposition.getRepository()));
        }
        return microserviceMetrics;
    }

}
