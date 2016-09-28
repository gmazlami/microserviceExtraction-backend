package git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import main.Configs;
import models.Repository;

public class GitClient {

	private Configs config;
	
	private Repository repo;
	
	public GitClient(Repository repo, Configs config){
		this.repo = repo;
		this.config = config;
	}
	
	public void cloneRepository() throws Exception {
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId())).call();
    	git.close();
	}
	
	public org.eclipse.jgit.lib.Repository getGitRepository() throws IOException {
		String path = config.localRepositoryDirectory + "/" + repo.getName()+"_"+repo.getId()+"/.git";
		File file = new File(config.localRepositoryDirectory + "/" + repo.getName()+"_"+repo.getId()+"/.git");
		org.eclipse.jgit.lib.Repository repository = new FileRepositoryBuilder().setGitDir(file)
				  .readEnvironment() // scan environment GIT_* variables
				  .findGitDir() // scan up the file system tree
				  .build();
		return repository;
	}
	
	public List<RevCommit> getCommitLog() throws Exception{
		Git git = new Git(getGitRepository());
		Iterable<RevCommit> log = git.log().call(); 
		git.close();
		List<RevCommit> logList = new ArrayList<>();
		log.forEach(logList::add);
		return logList;
	}
	
	public RepositoryHistory getChangeHistory() throws Exception{
		org.eclipse.jgit.lib.Repository repository = getGitRepository();
		Git git = new Git(repository);
		ObjectReader reader = repository.newObjectReader();
		RevWalk walk = new RevWalk(repository);
				
		
		List<RevCommit> log = getCommitLog();
		
		Map<ObjectId,List<DiffEntry>> diffHistory = new HashMap<>();
		
		for(int i=0; i < log.size() - 1; i++){
			RevCommit first = log.get(i);
			RevCommit second = log.get(i+1);
			
			RevTree firstTree = walk.parseTree(first.getTree().getId());
			RevTree secondTree = walk.parseTree(second.getTree().getId());
			
    		CanonicalTreeParser firstTreeIter = new CanonicalTreeParser();
    		firstTreeIter.reset(reader, firstTree);
    		
    		CanonicalTreeParser secondTreeIter = new CanonicalTreeParser();
    		secondTreeIter.reset(reader, secondTree);
    		
            List<DiffEntry> diffs = git.diff().setNewTree(firstTreeIter).setOldTree(secondTreeIter).call();
            diffHistory.put(second, diffs);
    		
		}
		log.remove(0);
		git.close();
		walk.close();
		return new RepositoryHistory(log, diffHistory);
	}
	
	
}
