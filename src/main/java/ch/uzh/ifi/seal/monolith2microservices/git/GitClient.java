package ch.uzh.ifi.seal.monolith2microservices.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.uzh.ifi.seal.monolith2microservices.services.git.FilterService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

public class GitClient {

	private Configs config;
	
	private GitRepository repo;
	
	public GitClient(GitRepository repo, Configs config){
		this.repo = repo;
		this.config = config;
	}
	
	public void cloneRepository() throws Exception {
    	Git git = Git.cloneRepository().setURI(repo.getRemotePath()).setDirectory(new File(config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId())).call();
    	git.close();
	}
	
	public Repository getGitRepository() throws IOException {
		File file = new File(config.localRepositoryDirectory + "/" + repo.getName()+"_"+repo.getId()+"/.git");
		return new FileRepositoryBuilder().setGitDir(file).readEnvironment().findGitDir().build();
	}
	
	public List<RevCommit> getCommitLog() throws Exception{
		Git git = new Git(getGitRepository());
		Iterable<RevCommit> log = git.log().call(); 
		git.close();
		List<RevCommit> logList = new ArrayList<>();
		log.forEach(logList::add);
		return logList;
	}
	
	public List<ChangeEvent> getChangeEvents() throws Exception {
		List<ChangeEvent> changeHistory = new ArrayList<>();

		FilterService filterService = new FilterService();

		Repository repository = getGitRepository();
		Git git = new Git(repository);
		RevWalk walk = new RevWalk(repository);
		ObjectReader reader = repository.newObjectReader();
		List<RevCommit> log = getCommitLog();

		RevCommit first, second;
		RevTree firstTree, secondTree;
		CanonicalTreeParser firstTreeIter = new CanonicalTreeParser();
		CanonicalTreeParser secondTreeIter = new CanonicalTreeParser();
		ChangeEvent event;
		
		for(int i=0; i < log.size() - 1; i++){
			first = log.get(i);
			second = log.get(i+1);
			
			firstTree = walk.parseTree(first.getTree().getId());
			secondTree = walk.parseTree(second.getTree().getId());
			
    		firstTreeIter.reset(reader, firstTree);
    		secondTreeIter.reset(reader, secondTree);
    		
            List<DiffEntry> diffs = git.diff().setNewTree(firstTreeIter).setOldTree(secondTreeIter).call();
			diffs = filterService.filterBlackList(diffs);
            event = new ChangeEvent(first.getCommitTime(),diffs,first);
            event.setAuthorEmailAddress(second.getAuthorIdent().getEmailAddress());
            changeHistory.add(event);
            
		}
		
		git.close();
		walk.close();
		return changeHistory.stream().filter(changeEvent -> changeEvent.getChangedfiles().size() > 0).collect(Collectors.toList());
	}
	
}
