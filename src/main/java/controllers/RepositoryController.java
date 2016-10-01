package controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dtos.RepositoryDTO;
import models.GitRepository;
import models.persistence.ClassRepository;
import models.persistence.RepositoryRepository;
import services.decomposition.DecompositionService;
import services.decomposition.Decompositor;
import services.git.GitCloneService;
import services.git.HistoryService;
import services.git.ParsingService;

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
	private GitCloneService gitCloneService;
	
	@Autowired
	private ParsingService parsingService;
	
	@Autowired
	private HistoryService analysisService;
	
	@Autowired
	private DecompositionService decompositionService;
	
    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello World!";
    }
    
    
    @RequestMapping(value="/repositories", method=RequestMethod.POST)
    public GitRepository addRepository(@RequestBody RepositoryDTO repo) throws Exception{
    	
    	GitRepository r = new GitRepository();
    	r.setName(repo.getName());
    	r.setRemotePath(repo.getUri());
    	
    	GitRepository saved = repository.save(r);
    	gitCloneService.processRepository(r);
    	
    	return saved;	
    }
    
    @RequestMapping(value="/repositories/{repoId}/decompose", method=RequestMethod.PUT)
    public ResponseEntity<String> triggerDecomposition(@PathVariable Long repoId){
    	GitRepository repo = repository.findById(repoId);
    	decompositionService.process(repo);
    	return new ResponseEntity<String>("OK", HttpStatus.OK);
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
