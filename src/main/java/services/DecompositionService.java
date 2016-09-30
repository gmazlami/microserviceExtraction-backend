package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import models.GitRepository;
import services.decomposition.Decompositor;

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
