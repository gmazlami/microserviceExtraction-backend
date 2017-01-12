package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.services.git.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by gmazlami on 1/12/17.
 */

@Service
public class EvaluationService {

    private Logger logger = LoggerFactory.getLogger(EvaluationService.class);

    @Autowired
    AuthorService authorService;


    public EvaluationMetrics computeMetrics(Decomposition decomposition){
        Map<Long,Set<String>> microserviceAuthorMap = computeAuthorMap(decomposition);

        EvaluationMetrics metrics = new EvaluationMetrics();
        metrics.setDecomposition(decomposition);
        metrics.setContributorsPerMicroservice(computeContributorPerMicroservice(decomposition,microserviceAuthorMap));
        metrics.setContributorOverlapping(computeContributorOverlapping(decomposition,microserviceAuthorMap));



        return null;
    }

    private double computeContributorPerMicroservice(Decomposition decomposition, Map<Long,Set<String>> microserviceAuthorMap){
        return microserviceAuthorMap.values().stream().map(set -> set.size()).mapToInt(Integer::intValue).sum() / decomposition.getServices().size();
    }

    private double computeContributorOverlapping(Decomposition decomposition, Map<Long,Set<String>> microserviceAuthorMap){
        Set<Long> microserviceIds = microserviceAuthorMap.keySet();

        List<Integer> overlappingContributors = new ArrayList<>();

        microserviceIds.forEach(firstServiceId -> {
            microserviceIds.forEach(secondServiceId -> {
                if(firstServiceId != secondServiceId){
                    overlappingContributors.add(getNumberOfOverlappingContributors(microserviceAuthorMap.get(firstServiceId),microserviceAuthorMap.get(secondServiceId)));
                }
            });
        });

        return overlappingContributors.stream().mapToInt(Integer::intValue).sum() / overlappingContributors.size();
    }

    private void computeMicroserviceSizeKLOC(Decomposition decomposition){
        //TODO:
    }

    private void computeMicroserviceSizeClasses(Decomposition decomposition){
        //TODO:
    }

    private void computeServiceSimilarity(Decomposition decomposition){
        //TODO:
    }

    private Map<Long, Set<String>> computeAuthorMap(Decomposition decomposition){
        Map<Long,Set<String>> microserviceAuthorMap = new HashMap<>();

        decomposition.getServices().forEach(microservice ->{

            Set<String> authorSet = new HashSet<String>();

            microservice.getNodes().forEach(classNode -> {
                try{
                    authorSet.addAll(authorService.getContributingAuthors(decomposition.getRepository(), classNode.getId()));
                }catch (Exception e){
                    logger.error("Error during computation of authorSet for ContributorPerMicroservice metric!");
                    logger.info(e.getMessage());
                }
            });

            microserviceAuthorMap.put(microservice.getId(), authorSet);

        });

        return microserviceAuthorMap;
    }

    public int getNumberOfOverlappingContributors(Set<String> firstSet, Set<String> secondSet){
        Set<String> intersection = new HashSet<>(firstSet);
        intersection.retainAll(secondSet);
        return intersection.size();
    }
}
