package ch.uzh.ifi.seal.monolith2microservices.services.git;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

@Service
public class GitCloneService {

	private static final Logger logger = LoggerFactory.getLogger(GitCloneService.class);
	
	private final String FILESYSTEM_DELIMITER = "/";
	private final String ID_NAME_DELIMITER = "_";

	@Autowired
	private Configs config;
	
    @Async
    public void processRepository(GitRepository repo) throws Exception{
    	logger.info("Cloning repository "+ repo.getRemotePath() +" ...");
    	final String localRepoPath = config.localRepositoryDirectory + FILESYSTEM_DELIMITER + repo.getName() + ID_NAME_DELIMITER + repo.getId();
    	
    	// clone the repository from the remote location to the local filesystem
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(localRepoPath)).call();
    	git.close();
    	
    	logger.info("Cloned.");
    }
    
}
