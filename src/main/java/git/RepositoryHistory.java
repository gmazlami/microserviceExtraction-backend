package git;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class RepositoryHistory {

	private List<RevCommit> log;
	
	private Map<ObjectId, List<DiffEntry>> diffs;
	
	public RepositoryHistory(List<RevCommit> log, Map<ObjectId, List<DiffEntry>> diffs){
		this.log = log;
		this.diffs = diffs;
	}

	public List<DiffEntry> getDiffsForCommit(ObjectId id){
		return diffs.get(id);
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
