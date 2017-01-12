package ch.uzh.ifi.seal.monolith2microservices.services.git;

import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.utils.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gmazlami on 1/12/17.
 */
@Service
public class AuthorService {

    @Autowired
    private HistoryService historyService;

    public Set<String> getContributingAuthors(GitRepository gitRepo, String filePath) throws Exception{
        List<ChangeEvent> history = historyService.computeChangeEvents(gitRepo);
        Set<String> contributingAuthors = new HashSet<>();
        history.forEach(event -> {
            event.getChangedfiles().forEach(changedFile -> {
                if(changedFile.equals(filePath)){
                    contributingAuthors.add(event.getAuthorEmailAddress());
                }
            });
        });
        return contributingAuthors;
    }
}
