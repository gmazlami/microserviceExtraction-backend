package services.git;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import git.GitClient;
import main.utils.Configs;
import models.ChangeEvent;
import models.GitRepository;
import models.persistence.RepositoryRepository;

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
		return gitClient.getChangeHistory().getChangeHistory().stream().filter(changeEvent -> changeEvent.getChangedfiles().size() > 1).collect(Collectors.toList());
	}
	
	/**
	 * Returns the history of a repository (@param repo) as a list of changes made at each commit.
	 * The return format is a list of lists of @{DiffEntry}, where each @{DiffEntry} corresponds to a changed file in a commit. 
	 * @param repo
	 * @return
	 * @throws Exception
	 */
	public List<List<DiffEntry>> computeRepositoryHistory(GitRepository repo) throws Exception{
		GitClient gitClient = new GitClient(repo, config);
		return filterDiffs(gitClient.getChangeHistory().getDiffHistory());
	}
	
	/*
	 * Filters out all DiffEntry instances that are not of DiffEntry.ChangeType.ADD or DiffEntry.ChangeType.MODIFY
	 * and don't end in .java
	 */
	private List<List<DiffEntry>> filterDiffs(List<List<DiffEntry>> originalHistory){
		//TODO: rewrite for ChangeEvent, filter out all ChangeEvents with less than 2 changed classes
		
		//Define predicate to filter only files that were added or modified and end with a .java file ending
		Predicate<DiffEntry> isAddOrModify = (entry) ->{
			if(entry.getChangeType() == DiffEntry.ChangeType.ADD || entry.getChangeType() == DiffEntry.ChangeType.MODIFY){
				if(entry.getNewPath().endsWith(".java")){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}; 

		//result list to contain filtered history DiffEntry elements
		List<List<DiffEntry>> filteredHistory = new ArrayList<>();
		
		//Apply filter predicate to the list
		for(List<DiffEntry> fileDiffs: originalHistory){
			List<DiffEntry> eligibleDiffs = new ArrayList<>();
			
			for(DiffEntry d : fileDiffs){
				if (isAddOrModify.test(d)){
					eligibleDiffs.add(d);
				}
			}
			if(eligibleDiffs.size() > 0){
				filteredHistory.add(eligibleDiffs);
			}
		}
		return filteredHistory;
	}
	
}
