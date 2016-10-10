package ch.uzh.ifi.seal.monolith2microservices.services.git;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.git.GitClient;
import ch.uzh.ifi.seal.monolith2microservices.main.utils.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.persistence.RepositoryRepository;

@Service
public class HistoryService {
	
	@Autowired
	Configs config;
	
	@Autowired
	RepositoryRepository repository;
	
	/**
	 * Returns the history of a repository (@param repo) as a list of changes made at each commit.
	 * The return format is a list  @link{ChangeEvent}, where each @{ChangeEvent} corresponds to a commit. 
	 * @param repo
	 * @return
	 * @throws Exception
	 */
	public List<ChangeEvent> computeChangeEvents(GitRepository repo) throws Exception{
		GitClient gitClient = new GitClient(repo, config);
		return gitClient.getChangeHistory().getChangeHistory().stream().filter(changeEvent -> changeEvent.getChangedfiles().size() > 0).collect(Collectors.toList());
	}
}
