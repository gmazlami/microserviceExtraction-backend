package services.decomposition;

import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.GitRepository;
import models.LogicalCoupling;
import services.AnalysisService;
import services.analysis.LogicalCouplingService;

@Service
public class LogicalCouplingDecompositor implements Decompositor {

	
	@Autowired
	AnalysisService analysisService;
	
	@Autowired
	LogicalCouplingService logicalCouplingService;
	
	@Override
	public void decompose(GitRepository repo) {
		try{
			List<List<DiffEntry>> history = analysisService.processRepository(repo); 
			List<LogicalCoupling> couplings = logicalCouplingService.computeLogicalCouplings(history, repo);
			//TODO: map couplings to Microservices using mapper
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
