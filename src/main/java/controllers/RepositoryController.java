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
import models.persistence.ClassRepository;
import models.persistence.RepositoryRepository;
import models.Class;
import services.GitCloneService;

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

    @RequestMapping(value="/test", method=RequestMethod.GET)
    public Class test(){
    	
    	Class cls = new Class();
    	cls.setName("TestClass");
    	cls.setPackageName("com.java.test");
    	cls.setFilePath("/some/path/to/file.java");
    	
    	Repository repo = new Repository();
    	repo.setName("my-test-repo");
    	repo.setRemotePath("https://github.com/my-test-repo");
    	
    	repository.save(repo);
    	
    	cls.setRepo(repo);
    	
    	classRepository.save(cls);
    	
    	return cls;
    	
    }
    
}
