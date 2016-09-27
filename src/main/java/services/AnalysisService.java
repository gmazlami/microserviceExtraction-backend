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

@Service
public class AnalysisService {
	
	@Autowired
	Configs config;
	
	
	@Async
	public void processRepository(Repository repo) throws Exception{
		GitClient gitClient = new GitClient(repo, config);
		
		
		List<List<DiffEntry>> diffHistory = filterDiffs(gitClient.getChangeHistory().getDiffHistory());
		
	}
	
	private List<List<DiffEntry>> filterDiffs(List<List<DiffEntry>> originalHistory){
		Predicate<DiffEntry> isAddOrModify = (entry) ->{
			if(entry.getChangeType() == DiffEntry.ChangeType.ADD || entry.getChangeType() == DiffEntry.ChangeType.MODIFY){
				return true;
			}else{
				return false;
			}
		}; 
		
		List<List<DiffEntry>> filteredHistory = new ArrayList<>();
		
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
