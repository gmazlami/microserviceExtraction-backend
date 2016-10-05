package git;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

import models.ChangeEvent;

public class RepositoryHistory {

	private List<RevCommit> log;
	
	private Map<ObjectId, List<DiffEntry>> diffs;
	
	private List<ChangeEvent> changeHistory;
	
	public RepositoryHistory(List<RevCommit> log, Map<ObjectId, List<DiffEntry>> diffs, List<ChangeEvent> changeHistory ){
		this.log = log;
		this.diffs = diffs;
		this.changeHistory = changeHistory;
	}

	public List<DiffEntry> getDiffsForCommit(ObjectId id){
		return diffs.get(id);
	}
	
	public List<ChangeEvent> getChangeHistory(){
		return this.changeHistory;
	}
	
	public List<List<DiffEntry>> getDiffHistory(){
		List<List<DiffEntry>> history = new ArrayList<>();
		
		for(RevCommit commit : log){
			history.add(diffs.get(commit.getId()));
		}
		
		return history;
	}

	public List<RevCommit> getCommitLog(){
		return this.log;
	}
}
