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
import models.GitRepository;
import models.persistence.ClassRepository;
import models.persistence.RepositoryRepository;
import services.AnalysisService;
import services.GitCloneService;
import services.ParsingService;

@Configuration
@EnableAutoConfiguration
@RestController
@Component
public class RepositoryController {
	

	@Autowired
	private RepositoryRepository repository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private GitCloneService service;
	
	@Autowired
	private ParsingService parsingService;
	
	@Autowired
	private AnalysisService analysisService;
	
    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello World!";
    }
    
    
    @RequestMapping(value="/repository", method=RequestMethod.POST)
    public GitRepository addRepository(@RequestBody RepositoryDTO repo) throws Exception{
    	
    	GitRepository r = new GitRepository();
    	r.setName(repo.getName());
    	r.setRemotePath(repo.getUri());
    	
    	GitRepository saved = repository.save(r);
    	
    	service.processRepository(r);
    	
    	return saved;	
    }
    
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String test() throws Exception{
    	
//    	Repository repo = new Repository();
//    	repo.setName("my-test-repo");
//    	repo.setRemotePath("https://github.com/my-test-repo");
//    	
//    	repository.save(repo);
    	
    	GitRepository reporepo = repository.findById(134L);
    	System.out.println(reporepo);
//    	parsingService.parseClasses(reporepo);
//    	analysisService.processRepository(reporepo);
    	
    	return "OK";
    	
    }
    
}
