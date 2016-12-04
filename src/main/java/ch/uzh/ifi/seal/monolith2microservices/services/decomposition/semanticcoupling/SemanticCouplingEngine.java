package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.ClassContent;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SemanticCouplingEngine {

    @Autowired
    private Configs config;

    public List<SemanticCoupling> computeCouplings(GitRepository repo) throws IOException{

        List<SemanticCoupling> couplings = new ArrayList<>();

        //Read class files (content) from repo
        String localRepoPath = config.localRepositoryDirectory + "/" + repo.getName() + "_" + repo.getId();

        Path repoDirectory = Paths.get(localRepoPath);

        ClassContentVisitor visitor = new ClassContentVisitor(repo,config);

        Files.walkFileTree(repoDirectory, visitor);

        List<ClassContent> classes = visitor.getClasses();

        for(ClassContent current: classes){
            for(ClassContent other: classes){
                if (!current.getFilePath().equals(other.getFilePath())) {
                    SemanticCoupling coupling = new SemanticCoupling();
                    coupling.setFirstClassFileName(current.getFilePath());
                    coupling.setSecondClassFileName(other.getFilePath());
                    coupling.setSimilarity(TfIdfWrapper.computeSimilarity(current.getTokenizedContent(), other.getTokenizedContent()));
                    couplings.add(coupling);
                }
            }
        }

        return couplings;

    }
}
