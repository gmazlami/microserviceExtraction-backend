package ch.uzh.ifi.seal.monolith2microservices.services.git;

import org.eclipse.jgit.diff.DiffEntry;

import java.util.List;

/**
 * Created by gmazlami on 11/28/16.
 */
public class FilterService {

    private final String[] BLACKLIST_RAILS = {"Gemfile", "Gemfile.lock", "routes.rb"};

    private final String[] BLACKLIST_JAVA = {"*.xml", "*.jar"};

    private final String[] BLACKLIST_DJANGO = {"settings.py", "urls.py", "__init__.py", "requirements.txt", "*.po"};

    private final String[] BLACKLIST_GENERAL = {"*.gitignore", "*.json"};



    public List<DiffEntry> filterBlackList(List<DiffEntry> diffFiles){
        return diffFiles;
    }
}
