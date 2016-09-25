package services;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import main.Configs;
import models.Repository;

@Service
public class GitCloneService {

	@Autowired
	private Configs config;
	
	
    @Async
    public void cloneRepo(Repository repo) throws Exception{
    	
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId())).call();
    	System.out.println("FINISHED CLONING!");
    }
}
