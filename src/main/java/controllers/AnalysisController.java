package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.Repository;
import models.persistence.RepositoryRepository;
import services.AnalysisService;



@RestController
@Component
@Configuration
@EnableAutoConfiguration
public class AnalysisController {
	
	@Autowired
	private AnalysisService service;
	
	@Autowired
	private RepositoryRepository repository;
	
	@RequestMapping(value="/analyze/{repositoryId}", method = RequestMethod.PUT)
	public Repository triggerAnalysis(@PathVariable Long repositoryId) throws Exception{
		
		Repository repo = repository.findById(repositoryId);
		
		service.processRepository(repo);
		
		return repo;
	}

}
