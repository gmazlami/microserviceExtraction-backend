package ch.uzh.ifi.seal.monolith2microservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.persistence.RepositoryRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;



@RestController
@Component
@Configuration
@EnableAutoConfiguration
public class AnalysisController {
	
	@Autowired
	private HistoryService service;
	
	@Autowired
	private RepositoryRepository repository;
	
	@RequestMapping(value="/analyze/{repositoryId}", method = RequestMethod.PUT)
	public GitRepository triggerAnalysis(@PathVariable Long repositoryId) throws Exception{
		
		GitRepository repo = repository.findById(repositoryId);
		
		service.computeRepositoryHistory(repo);
		
		return repo;
	}

}
