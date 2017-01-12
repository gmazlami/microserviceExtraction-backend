package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.services.git.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by gmazlami on 1/12/17.
 */

@Service
public class EvaluationService {

    private Logger logger = LoggerFactory.getLogger(EvaluationService.class);

    @Autowired
    AuthorService authorService;

    public double computeContributorPerMicroservice(Decomposition decomposition){
        Map<Long,Integer> microserviceAuthorCountMap = new HashMap<>();

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
            microserviceAuthorCountMap.put(microservice.getId(), authorSet.size());
        });

        return microserviceAuthorCountMap.values().stream().mapToInt(Integer::intValue).sum() / decomposition.getServices().size();
    }

    public void computeContributorOverlapping(Decomposition decomposition){
        
        //TODO:
    }

    public void computeMicroserviceSizeKLOC(Decomposition decomposition){
        //TODO:
    }

    public void computeMicroserviceSizeClasses(Decomposition decomposition){
        //TODO:
    }

    public void computeServiceSimilarity(Decomposition decomposition){
        //TODO:
    }
}
