package services.decomposition.logicalcoupling;

import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.GitRepository;
import models.LogicalCoupling;
import models.Microservice;
import services.decomposition.Decompositor;
import services.git.HistoryService;
import services.reporting.TextFileReport;

@Service
public class LogicalCouplingDecompositor implements Decompositor {

	
	@Autowired
	HistoryService analysisService;
	
	@Autowired
	LogicalCouplingService logicalCouplingService;
	
	@Autowired
	LogicalCouplingToMicroserviceMapper logicalCouplingToMicroserviceMapper;
	
	@Override
	public void decompose(GitRepository repo) {
		try{
			System.out.println("Computing history...");
			List<List<DiffEntry>> history = analysisService.computeRepositoryHistory(repo);
			
			
			System.out.println("Computing logical couplings...");
			List<LogicalCoupling> couplings = logicalCouplingService.computeLogicalCouplings(history, repo);
			
			System.out.println("Mapping to microservices...");
			List<Microservice> microservices = logicalCouplingToMicroserviceMapper.mapToMicroservices(couplings); 
			
			TextFileReport.generate(repo, microservices);
			System.out.println("Finished.");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
