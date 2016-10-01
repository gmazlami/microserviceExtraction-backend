package services.git;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import git.ClassVisitor;
import main.utils.Configs;
import models.Class;
import models.GitRepository;
import models.persistence.ClassRepository;

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
    	final String localRepoPath = config.localRepositoryDirectory + FILESYSTEM_DELIMITER + repo.getName() + ID_NAME_DELIMITER + repo.getId();
    	
    	// clone the repository from the remote location to the local filesystem
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(localRepoPath)).call();
    	git.close();
    	
		// read/parse the classes from the local copy of the repository
    	readClassFiles(repo, localRepoPath);
    }
    
    private void readClassFiles(GitRepository repo, String localRepoPath) throws Exception{
		ClassVisitor visitor = new ClassVisitor(repo, config);
		Files.walkFileTree(Paths.get(localRepoPath), visitor);
		List<Class> classes = visitor.getClasses();
		classes.forEach(cls -> classRepository.save(cls));
    }
}
