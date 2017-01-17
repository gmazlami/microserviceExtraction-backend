package ch.uzh.ifi.seal.monolith2microservices.models.git;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

public class ChangeEvent {
	

	public ChangeEvent(int timestamp, List<DiffEntry> diffEntries, RevCommit commit){
		this.timestampInSeconds = timestamp;
		this.commitObject = commit;
		diffEntries.forEach(changedfiles::add);
	}
	
	private int timestampInSeconds;
	
	private String authorEmailAddress;
	
	private List<DiffEntry> changedfiles = new ArrayList<>();

	private Map<String,String> changedFileNames = new HashMap<>();

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

	public List<String> getChangedFileNames(){
		return this.changedFileNames.keySet().stream().collect(Collectors.toList());
	}

	public void addFileName(String fileName){
		this.changedFileNames.put(fileName,fileName);
	}

	public void removeFileName(String fileName){
		this.changedFileNames.remove(fileName);
	}

	public void renameChangedFileName(String oldName, String newName){
		this.changedFileNames.remove(oldName);
		this.changedFileNames.put(newName,newName);
	}

	@Override
	public String toString() {
		return "ChangeEvent [timestampInSeconds=" + timestampInSeconds + ", changedfiles=" + changedfiles
				+ ", commitObject=" + commitObject + "]";
	}
	
}
