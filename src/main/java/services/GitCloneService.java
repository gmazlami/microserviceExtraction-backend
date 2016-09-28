package services;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import main.Configs;
import models.GitRepository;

@Service
public class GitCloneService {

	@Autowired
	private Configs config;
	
	/*
	 * Clones a remote Git repository to a local folder.
	 * The local folder is determined by config.localRepositoryDirectory
	 */
    @Async
    public void cloneRepo(GitRepository repo) throws Exception{
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId())).call();
    	git.close();
    }
}
