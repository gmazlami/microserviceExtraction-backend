package models;

import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

public class ChangeEvent {

	public ChangeEvent(int timestamp, List<DiffEntry> diffEntries, RevCommit commit){
		this.timestampInSeconds = timestamp;
		this.changedfiles = diffEntries;
		this.commitObject = commit;
	}
	
	private int timestampInSeconds;
	
	private List<DiffEntry> changedfiles;
	
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

	@Override
	public String toString() {
		return "ChangeEvent [timestampInSeconds=" + timestampInSeconds + ", changedfiles=" + changedfiles
				+ ", commitObject=" + commitObject + "]";
	}
	
	
	
}
