package ch.uzh.ifi.seal.monolith2microservices.services.git;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.git.ClassVisitor;
import ch.uzh.ifi.seal.monolith2microservices.main.utils.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.Class;
import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.persistence.ClassRepository;

@Service
public class GitCloneService {

	private final String FILESYSTEM_DELIMITER = "/";
	private final String ID_NAME_DELIMITER = "_";

	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private Configs config;
	
    @Async
    public void processRepository(GitRepository repo) throws Exception{
    	System.out.println("Processing...");
    	final String localRepoPath = config.localRepositoryDirectory + FILESYSTEM_DELIMITER + repo.getName() + ID_NAME_DELIMITER + repo.getId();
    	
    	// clone the repository from the remote location to the local filesystem
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(localRepoPath)).call();
    	git.close();
    	
    	System.out.println("Cloned.");
    	
		// read/parse the classes from the local copy of the repository
    	readClassFiles(repo, localRepoPath);
    }
    
    private void readClassFiles(GitRepository repo, String localRepoPath) throws Exception{
    	System.out.println("Reading class files...");
		ClassVisitor visitor = new ClassVisitor(repo, config);
		System.out.println("Starting walkFileTree...");
		Files.walkFileTree(Paths.get(localRepoPath), visitor);
		List<Class> classes = visitor.getClasses();
		classes.forEach(cls -> classRepository.save(cls));
		System.out.println("Finished parsing classes.");
    }
}
