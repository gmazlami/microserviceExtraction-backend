package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.MicroserviceMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
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



    public MicroserviceMetrics from(Component microservice, GitRepository repo, List<ChangeEvent> history) throws IOException{
        MicroserviceMetrics metrics = new MicroserviceMetrics(microservice);

        Map<String,Set<String>> fileAuthorMap = generateAuthorMap(history);

        metrics.setContributors(computeAuthorSet(microservice,fileAuthorMap));
        metrics.setSizeLoc(computeSizeInLoc(microservice, repo));
        return metrics;
    }

    private Map<String,Set<String>> generateAuthorMap(List<ChangeEvent> history){
        Map<String,Set<String>> fileAuthorMap = new HashMap<>();

        for(ChangeEvent event: history){
            for(String fileName: event.getChangedFileNames()){
                if(fileAuthorMap.get(fileName)==null){
                    Set<String> authorSet = new HashSet<>();
                    authorSet.add(event.getAuthorEmailAddress());
                    fileAuthorMap.put(fileName,authorSet);
                }else{
                    Set<String> authorSet = fileAuthorMap.get(fileName);
                    authorSet.add(event.getAuthorEmailAddress());
                    fileAuthorMap.put(fileName,authorSet);
                }
            }
        }

        return fileAuthorMap;
    }


    private  Set<String> computeAuthorSet(Component microservice, Map<String, Set<String>> authorMap){
        Set<String> authorSet = new HashSet<>();

        microservice.getNodes().forEach(classNode -> {
            authorSet.addAll(authorMap.get(classNode.getId()));
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
