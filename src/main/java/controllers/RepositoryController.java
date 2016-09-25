package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dtos.RepositoryDTO;
import main.Configs;
import models.Repository;
import models.persistence.RepositoryRepository;
import services.GitCloneService;

@Configuration
@EnableAutoConfiguration
@RestController
@Component
public class RepositoryController {
	

	@Autowired
	private RepositoryRepository repository;
	
	@Autowired
	private Configs config;
	
	@Autowired
	private GitCloneService service;
	
    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello World!";
    }
    
    
    @RequestMapping(value="/repository", method=RequestMethod.POST)
    public Repository addRepository(@RequestBody RepositoryDTO repo) throws Exception{
    	
    	Repository r = new Repository();
    	r.setName(repo.getName());
    	r.setRemotePath(repo.getUri());
    	
    	Repository saved = repository.save(r);
    	
    	service.cloneRepo(r);
    	
    	return saved;	
    	
    	
    	
    }
    
}
