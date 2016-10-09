package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.models.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.Microservice;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.timeseries.LogicalCouplingEngine;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;
import ch.uzh.ifi.seal.monolith2microservices.services.reporting.TextFileReport;

@Service
public class LogicalCouplingDecompositor implements Decompositor {

	
	@Autowired
	HistoryService analysisService;
	
	@Autowired
	LogicalCouplingService logicalCouplingService;
	
	@Autowired
	LogicalCouplingEngine logicalCouplingEngine;
	
	@Autowired
	LogicalCouplingToMicroserviceMapper logicalCouplingToMicroserviceMapper;
	
	@Override
	public void decompose(GitRepository repo) {
		try{
			System.out.println("Computing history...");
			List<ChangeEvent> changeHistory = analysisService.computeChangeEvents(repo);
			
			System.out.println("Computing logical couplings...");
			List<LogicalCoupling> couplings = logicalCouplingEngine.computeCouplings(changeHistory, 60600);
			logicalCouplingEngine.reset();
			System.out.println("Computed logical couplings.");
			couplings.forEach(c -> {
				System.out.println("t_start: " + c.getStartTimestamp() + " t_end: " + c.getEndTimestamp() + " score: " + c.getScore() + " files: " + c.getClassFiles());
			});
			
//			System.out.println("Mapping to microservices...");
//			List<Microservice> microservices = logicalCouplingToMicroserviceMapper.mapToMicroservices(couplings); 
//			
//			TextFileReport.generate(repo, microservices);
//			System.out.println("Finished.");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
