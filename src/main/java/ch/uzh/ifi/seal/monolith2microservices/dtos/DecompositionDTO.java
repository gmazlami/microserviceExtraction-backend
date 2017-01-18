package ch.uzh.ifi.seal.monolith2microservices.dtos;

import ch.uzh.ifi.seal.monolith2microservices.models.DecompositionParameters;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

/**
 * Created by gmazlami on 1/17/17.
 */
public class DecompositionDTO {

    private GitRepository repo;

    private long decompositionId;

    private DecompositionParameters parameters;

    public DecompositionParameters getParameters(){
        return this.parameters;
    }

    public GitRepository getRepo(){
        return this.repo;
    }

    public long getDecompositionId(){
        return this.decompositionId;
    }

    public void setParameters(DecompositionParameters parameters){
        this.parameters = parameters;
    }

    public void setRepo(GitRepository repo){
        this.repo = repo;
    }

    public void setDecompositionId(long id){
        this.decompositionId = id;
    }
}
