package ch.uzh.ifi.seal.monolith2microservices.services.git;

import org.eclipse.jgit.diff.DiffEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by gmazlami on 11/28/16.
 */
public class FilterService {

    private final String[] BLACKLIST = {"Gemfile", "Gemfile.lock", "routes.rb", "*.xml", "*.jar","settings.py", "urls.py", "__init__.py", "requirements.txt", "*.po","*.gitignore", "*.json"};


    //Define predicate to filter only files that were added or modified and conform to the file patterns
    private Predicate<DiffEntry> filterPredicate = (entry) ->{
        //filter out Git changes that did not add or modify files
        if(entry.getChangeType() == DiffEntry.ChangeType.ADD ||
                entry.getChangeType() == DiffEntry.ChangeType.MODIFY ||
                entry.getChangeType() == DiffEntry.ChangeType.RENAME ||
                entry.getChangeType() == DiffEntry.ChangeType.DELETE ){
            //filter out anything not related to Java, Python or Ruby
            if(entry.getNewPath().endsWith(".java") ||
                    entry.getNewPath().endsWith(".rb") ||
                    entry.getNewPath().endsWith(".py") ||
                    (entry.getNewPath().equals("/dev/null") && (entry.getOldPath().endsWith(".py") || entry.getOldPath().endsWith(".java") || entry.getOldPath().endsWith(".rb")))){
                //filter out BLACKLIST files
                for(String pattern : BLACKLIST){
                    if(entry.getNewPath().endsWith(pattern)){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    };


    public List<DiffEntry> filterBlackList(List<DiffEntry> diffFiles){
        List<DiffEntry> filteredEntries = new ArrayList<>();
        diffFiles.stream().filter(filterPredicate).forEach(filteredEntries::add);
        return filteredEntries;
    }



}
