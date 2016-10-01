package services.decomposition;

import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.GitRepository;
import models.LogicalCoupling;
import models.Microservice;
import services.AnalysisService;
import services.analysis.LogicalCouplingService;
import services.mappers.LogicalCouplingToMicroserviceMapper;

@Service
public class LogicalCouplingDecompositor implements Decompositor {

	
	@Autowired
	AnalysisService analysisService;
	
	@Autowired
	LogicalCouplingService logicalCouplingService;
	
	@Autowired
	LogicalCouplingToMicroserviceMapper logicalCouplingToMicroserviceMapper;
	
	@Override
	public void decompose(GitRepository repo) {
		try{
			List<List<DiffEntry>> history = analysisService.processRepository(repo); 
			List<LogicalCoupling> couplings = logicalCouplingService.computeLogicalCouplings(history, repo);
			List<Microservice> microservices = logicalCouplingToMicroserviceMapper.mapToMicroservices(couplings); 
			
			microservices.forEach(service -> System.out.println(service.getClasses()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
