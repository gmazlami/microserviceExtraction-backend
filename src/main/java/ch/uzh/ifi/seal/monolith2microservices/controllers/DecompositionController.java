package ch.uzh.ifi.seal.monolith2microservices.controllers;

import ch.uzh.ifi.seal.monolith2microservices.dtos.DecompositionDTO;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.persistence.RepositoryRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors.ContributorCouplingDecompositionService;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.LogicalCouplingDecompositionService;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.SemanticCouplingDecompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    private LogicalCouplingDecompositionService logicalCouplingDecompositionService;

    @Autowired
    private ContributorCouplingDecompositionService contributorCouplingDecompositionService;

    @Autowired
    private SemanticCouplingDecompositionService semanticCouplingDecompositionService;



    @RequestMapping(value="/repositories/{repoId}/decompose/logicalcoupling", method=RequestMethod.PUT)
    public ResponseEntity<String> logicalCouplingDecomposition(@PathVariable Long repoId){
        GitRepository repo = repository.findById(repoId);
        logicalCouplingDecompositionService.process(repo);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @RequestMapping(value="/repositories/{repoId}/decompose/contributorCoupling", method=RequestMethod.PUT)
    public ResponseEntity<String> contributorCouplingDecomposition(@PathVariable Long repoId){
        GitRepository repo = repository.findById(repoId);
        contributorCouplingDecompositionService.process(repo);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @RequestMapping(value="/repositories/{repoId}/decompose/semanticCoupling", method=RequestMethod.PUT)
    public ResponseEntity<String> semanticCouplingDecomposition(@PathVariable Long repoId){
        GitRepository repo = repository.findById(repoId);
        semanticCouplingDecompositionService.process(repo);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value="/repositories/{repoId}/decomposition", method=RequestMethod.POST)
    public ResponseEntity<String> decomposition(@PathVariable Long repoId, @RequestBody DecompositionDTO decompositionDTO){
        System.out.println("--------------------- HERE! -----------------------------------");
        System.out.println(decompositionDTO);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


}
