package ch.uzh.ifi.seal.monolith2microservices.services.decomposition;

import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

public interface Decompositor {
	
	void decompose(GitRepository repo);

}
