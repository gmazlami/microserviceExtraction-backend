package ch.uzh.ifi.seal.monolith2microservices.controllers;

import ch.uzh.ifi.seal.monolith2microservices.conversion.GraphRepresentation;
import ch.uzh.ifi.seal.monolith2microservices.dtos.DecompositionDTO;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.persistence.RepositoryRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.DecompositionService;
import ch.uzh.ifi.seal.monolith2microservices.services.evaluation.EvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableAutoConfiguration
@RestController
@Component
/**
 * Created by gmazlami on 12/7/16.
 */
public class DecompositionController {


    @Autowired
    private RepositoryRepository repository;

    @Autowired
    private DecompositionService decompositionService;

    @Autowired
    private EvaluationService evaluationService;


    private Logger logger = LoggerFactory.getLogger(DecompositionController.class);


    @CrossOrigin
    @RequestMapping(value="/repositories/{repoId}/decomposition", method=RequestMethod.POST)
    public ResponseEntity<Set<GraphRepresentation>> decomposition(@PathVariable Long repoId, @RequestBody DecompositionDTO decompositionDTO){
        logger.info(decompositionDTO.toString());

        //find repository to be decomposed
        GitRepository repo = repository.findById(repoId);

        //perform decomposition
        Decomposition decomposition = decompositionService.decompose(repo,decompositionDTO);

        // convert to graph representation for frontend
        Set<GraphRepresentation> graph = decomposition.getServices().stream().map(GraphRepresentation::from).collect(Collectors.toSet());

        // Compute evaluation metrics
        EvaluationMetrics metrics = evaluationService.performEvaluation(decomposition);

        return new ResponseEntity<>(graph,HttpStatus.OK);
    }


}
