package ch.uzh.ifi.seal.monolith2microservices.models.git;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

public class ChangeEvent {
	

	public ChangeEvent(int timestamp, List<DiffEntry> diffEntries, RevCommit commit){
		this.timestampInSeconds = timestamp;
		this.commitObject = commit;
		
		//add only the @link{DiffEntry} elements that are of the @link{DiffEntry.ChangeType} ADD or MODIFY
		diffEntries.stream().filter(isAddOrModify).forEach(changedfiles::add);
	}
	
	private int timestampInSeconds;
	
	private String authorEmailAddress;
	
	private List<DiffEntry> changedfiles = new ArrayList<>();
	
	private RevCommit commitObject;

	public int getTimestampInSeconds() {
		return timestampInSeconds;
	}

	public void setTimestampInSeconds(int timestampInSeconds) {
		this.timestampInSeconds = timestampInSeconds;
	}

	public List<DiffEntry> getChangedfiles() {
		return changedfiles;
	}

	public void setChangedfiles(List<DiffEntry> changedfiles) {
		this.changedfiles = changedfiles;
	}

	public RevCommit getCommitObject() {
		return commitObject;
	}

	public void setCommitObject(RevCommit commitObject) {
		this.commitObject = commitObject;
	}
	
	public String getAuthorEmailAddress() {
		return authorEmailAddress;
	}

	public void setAuthorEmailAddress(String authorEmailAddress) {
		this.authorEmailAddress = authorEmailAddress;
	}

	@Override
	public String toString() {
		return "ChangeEvent [timestampInSeconds=" + timestampInSeconds + ", changedfiles=" + changedfiles
				+ ", commitObject=" + commitObject + "]";
	}
	
	//Define predicate to filter only files that were added or modified and end with a .java file ending
	private Predicate<DiffEntry> isAddOrModify = (entry) ->{
		if(entry.getChangeType() == DiffEntry.ChangeType.ADD || entry.getChangeType() == DiffEntry.ChangeType.MODIFY){
			if(entry.getNewPath().endsWith(".java") || entry.getNewPath().endsWith(".rb") || entry.getNewPath().endsWith(".py")){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}; 
	
}
