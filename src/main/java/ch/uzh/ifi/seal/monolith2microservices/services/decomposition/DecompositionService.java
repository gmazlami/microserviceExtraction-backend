package ch.uzh.ifi.seal.monolith2microservices.services.decomposition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;

@Service
public class DecompositionService {

	@Autowired
	@Qualifier("logicalCouplingDecompositor")
	Decompositor decompositor;
	
	@Async
	public void process(GitRepository repo){
		decompositor.decompose(repo);
	}
	
}
