package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.MicroserviceMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.services.git.AuthorService;
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
 * Created by gmazlami on 1/12/17.
 */

@Service
public class DecompositionEvaluationService {

    private Logger logger = LoggerFactory.getLogger(DecompositionEvaluationService.class);

    @Autowired
    private Configs config;

    @Autowired
    AuthorService authorService;

    @Autowired
    MicroserviceSimilarityService similarityService;


    public EvaluationMetrics computeMetrics(Decomposition decomposition, List<MicroserviceMetrics> microserviceMetrics) throws IOException{
        EvaluationMetrics metrics = new EvaluationMetrics();
        metrics.setDecomposition(decomposition);
        metrics.setContributorsPerMicroservice(computeContributorPerMicroservice(microserviceMetrics));
        metrics.setContributorOverlapping(computeContributorOverlapping(microserviceMetrics));
        metrics.setAverageLoc(computeAverageLoc(microserviceMetrics));
        metrics.setAverageClassNumber(computeMicroserviceSizeClasses(microserviceMetrics));
        metrics.setSimilarity(computeServiceSimilarity(decomposition));
        metrics.setExecutionTimeMillisClustering(decomposition.getClusteringTime());
        metrics.setExecutionTimeMillisStrategy(decomposition.getStrategyTime());
        return metrics;
    }

    private double computeContributorPerMicroservice(List<MicroserviceMetrics> microserviceMetrics){
        return microserviceMetrics.stream().map(metric -> metric.getNumOfContributors()).mapToDouble(Double::doubleValue).sum() / microserviceMetrics.size();
    }

    private double computeContributorOverlapping(List<MicroserviceMetrics> microserviceMetrics){
        List<Integer> overlappingContributors = new ArrayList<>();
        microserviceMetrics.forEach( firstServiceMetric -> {
            microserviceMetrics.forEach( secondServiceMetric -> {
                overlappingContributors.add(getNumberOfOverlappingContributors(firstServiceMetric.getContributors(), secondServiceMetric.getContributors()));
            });
        });
        return overlappingContributors.stream().mapToInt(Integer::intValue).sum() / overlappingContributors.size();
    }

    private double computeAverageLoc(List<MicroserviceMetrics> microserviceMetrics){
        return microserviceMetrics.stream().map(metric -> metric.getSizeInLoc()).mapToInt(Integer::intValue).sum() / microserviceMetrics.size();
    }

    private double computeMicroserviceSizeClasses(List<MicroserviceMetrics> microserviceMetrics){
        return microserviceMetrics.stream().map(metric -> metric.getSizeInClasses()).mapToInt(Integer::intValue).sum() / microserviceMetrics.size();
    }

    private double computeServiceSimilarity(Decomposition decomposition) throws IOException{
        if(decomposition.getServices().size() > 1){
            List<Double> similarities = new ArrayList<>();
            for(Component firstService :  decomposition.getServices()){
                for(Component secondService: decomposition.getServices()){
                    if(firstService.getId() != secondService.getId()){
                        similarities.add(similarityService.computeServiceSimilarity(decomposition.getRepository(),firstService,secondService));
                    }
                }
            }
            return similarities.stream().mapToDouble(Double::doubleValue).sum() / similarities.size();
        }else{
            return 1d;
        }
    }


    public int getNumberOfOverlappingContributors(Set<String> firstSet, Set<String> secondSet){
        Set<String> intersection = new HashSet<>(firstSet);
        intersection.retainAll(secondSet);
        return intersection.size();
    }
}
