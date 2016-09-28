package services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import git.GitClient;
import main.Configs;
import models.Repository;
import models.persistence.RepositoryRepository;
import services.analysis.LogicalCouplingService;

@Service
public class AnalysisService {
	
	@Autowired
	Configs config;
	
	@Autowired
	RepositoryRepository repository;
	
	@Async
	public void processRepository(Repository repo) throws Exception{
		GitClient gitClient = new GitClient(repo, config);
		
		List<List<DiffEntry>> diffHistory = filterDiffs(gitClient.getChangeHistory().getDiffHistory());
		
		LogicalCouplingService engine = new LogicalCouplingService(diffHistory, repo);
		
		engine.computeLogicalCouplings();
		
	}
	
	private List<List<DiffEntry>> filterDiffs(List<List<DiffEntry>> originalHistory){
		
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
			filteredHistory.add(eligibleDiffs);
		}
		return filteredHistory;
	}
	
}
