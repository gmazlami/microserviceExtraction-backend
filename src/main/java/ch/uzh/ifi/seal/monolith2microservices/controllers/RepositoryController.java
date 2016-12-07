package ch.uzh.ifi.seal.monolith2microservices.controllers;


import ch.uzh.ifi.seal.monolith2microservices.dtos.RepositoryDTO;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.persistence.RepositoryRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.git.GitCloneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Configuration
@EnableAutoConfiguration
@RestController
@Component
public class RepositoryController {
	

	@Autowired
	private RepositoryRepository repository;
	
	@Autowired
	private GitCloneService gitCloneService;

    @RequestMapping(value="/repositories", method=RequestMethod.POST)
    public GitRepository addRepository(@RequestBody RepositoryDTO repo) throws Exception{
    	
    	GitRepository r = new GitRepository();
    	r.setName(repo.getName());
    	r.setRemotePath(repo.getUri());
    	
    	GitRepository saved = repository.save(r);
    	gitCloneService.processRepository(r);
    	
    	return saved;	
    }

	@RequestMapping(value="/repositories", method=RequestMethod.GET)
	public List<GitRepository> listRepositories() throws Exception{
		return repository.findAll();
	}
    
}
