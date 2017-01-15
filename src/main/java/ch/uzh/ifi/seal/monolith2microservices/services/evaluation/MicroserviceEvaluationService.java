package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.MicroserviceMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.services.git.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by gmazlami on 1/15/17.
 */
@Service
public class MicroserviceEvaluationService {

    private Logger logger = LoggerFactory.getLogger(MicroserviceEvaluationService.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Configs config;



    public MicroserviceMetrics from(Component microservice, GitRepository repo) throws IOException{
        MicroserviceMetrics metrics = new MicroserviceMetrics(microservice);
        metrics.setContributors(computeAuthorSet(microservice,repo));
        metrics.setSizeLoc(computeSizeInLoc(microservice, repo));
        return metrics;
    }



    private  Set<String> computeAuthorSet(Component microservice, GitRepository repo){
        Set<String> authorSet = new HashSet<String>();

        microservice.getNodes().forEach(classNode -> {
            try{
                authorSet.addAll(authorService.getContributingAuthors(repo, classNode.getId()));
            }catch (Exception e){
                logger.error("Error during computation of authorSet for ContributorPerMicroservice metric!");
                logger.info(e.getMessage());
            }
        });

        return authorSet;
    }

    private int computeSizeInLoc(Component microservice, GitRepository repo) throws IOException{

        List<String> filePaths = new ArrayList<>();
        microservice.getNodes().forEach(node -> filePaths.add(node.getId()));
        String pathPrefix = config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId();

        int lineCounter = 0;

        for(String filePath : filePaths){
            BufferedReader reader = Files.newBufferedReader(Paths.get(pathPrefix + "/" + filePath));
            while(reader.readLine() != null){
                lineCounter++;
            }
        }

        return lineCounter;
    }

}
