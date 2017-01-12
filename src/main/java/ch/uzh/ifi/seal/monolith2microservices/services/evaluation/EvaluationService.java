package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gmazlami on 1/12/17.
 */

@Service
public class EvaluationService {

    public void computeContributorPerMicroservice(List<Component> services){
        //TODO:
    }

    public void computeContributorOverlapping(List<Component> services){
        //TODO:
    }

    public void computeMicroserviceSizeKLOC(List<Component> services){
        //TODO:
    }

    public void computeMicroserviceSizeClasses(List<Component> services){
        //TODO:
    }

    public void computeServiceSimilarity(List<Component> services){
        //TODO:
    }
}
