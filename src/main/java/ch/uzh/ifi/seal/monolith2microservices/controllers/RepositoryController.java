package ch.uzh.ifi.seal.monolith2microservices.controllers;

import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors.ContributorCouplingDecompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.monolith2microservices.dtos.RepositoryDTO;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.persistence.RepositoryRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.LogicalCouplingDecompositionService;
import ch.uzh.ifi.seal.monolith2microservices.services.git.GitCloneService;

@Configuration
@EnableAutoConfiguration
@RestController
@Component
public class RepositoryController {
	

	@Autowired
	private RepositoryRepository repository;
	
	@Autowired
	private GitCloneService gitCloneService;
	
	@Autowired
	private LogicalCouplingDecompositionService logicalCouplingDecompositionService;

    @Autowired
    private ContributorCouplingDecompositionService contributorCouplingDecompositionService;

    @RequestMapping(value="/repositories", method=RequestMethod.POST)
    public GitRepository addRepository(@RequestBody RepositoryDTO repo) throws Exception{
    	
    	GitRepository r = new GitRepository();
    	r.setName(repo.getName());
    	r.setRemotePath(repo.getUri());
    	
    	GitRepository saved = repository.save(r);
    	gitCloneService.processRepository(r);
    	
    	return saved;	
    }
    
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
    
}
