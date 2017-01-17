package ch.uzh.ifi.seal.monolith2microservices.services.git;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.git.GitClient;
import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
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
		return gitClient.getChangeEvents();
	}


	public List<ChangeEvent> cleanHistory(List<ChangeEvent> events){
		List<ChangeEvent> cleanedEvents = setUpChangedFileNames(events);
		List<ChangeEvent> afterHandleDeleteEvents = handleDeletedFiles(cleanedEvents);
		List<ChangeEvent> afterHandleRenameEvents = correctRenamedFiles(afterHandleDeleteEvents);
		return afterHandleRenameEvents;
	}


	private List<ChangeEvent> setUpChangedFileNames(List<ChangeEvent> events){
		//set up initial filenames of changed files
		for(ChangeEvent event: events){
			event.getChangedfiles().stream().map(diffEntry -> diffEntry.getNewPath()).forEach(event::addFileName);
		}

		return events;
	}


	private List<ChangeEvent> handleDeletedFiles(List<ChangeEvent> events){
		List<String> deletedFileNames = new ArrayList<>();

		for(ChangeEvent event: events){
			for(DiffEntry entry: event.getChangedfiles()){
				if(entry.getNewPath().equals("/dev/null") && !entry.getOldPath().equals(entry.getNewPath())){ //file was deleted
					deletedFileNames.add(entry.getOldPath());
				}
			}

		}

		deletedFileNames.forEach(fileName -> {
			for(ChangeEvent event: events){
				for(DiffEntry entry : event.getChangedfiles()){
					if(entry.getOldPath().equals(fileName)){
						event.removeFileName(fileName);
					}
					if(entry.getNewPath().equals(fileName)){
						event.removeFileName(fileName);
					}
					if(entry.getNewPath().equals("/dev/null")){
						event.removeFileName("/dev/null");
					}
				}
			}

		});

		return events;
	}


	private List<ChangeEvent> correctRenamedFiles(List<ChangeEvent> events) {

		int counter = 0;
		for(ChangeEvent event: events){
			for (String fileName: event.getChangedFileNames()){
				String lastName = findLastName(fileName,events,counter);
				if (lastName != fileName){
					event.renameChangedFileName(fileName,lastName);
				}
				if(fileName.equals("/dev/null")){ // file was deleted

				}
			}
			counter++;
		}

		return events;
	}

	private String findLastName(String originalName, List<ChangeEvent> events, int startingIndex){
		String newName = originalName;
		for(int i = startingIndex; i < events.size(); i++){
			ChangeEvent event = events.get(i);

			for(DiffEntry entry: event.getChangedfiles()){
				if(entry.getOldPath().equals(originalName) && !entry.getNewPath().equals(entry.getOldPath())){
					newName = entry.getNewPath();
				}
			}
		}
		return newName;
	}
	
}
