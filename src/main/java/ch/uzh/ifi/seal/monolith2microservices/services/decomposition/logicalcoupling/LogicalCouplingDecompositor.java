package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.models.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.Microservice;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph.GraphMapper;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph.GraphToMicrserviceMapper;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.timeseries.LogicalCouplingEngine;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;
import ch.uzh.ifi.seal.monolith2microservices.services.reporting.TextFileReport;

@Service
public class LogicalCouplingDecompositor implements Decompositor {

	private static final Logger logger = LoggerFactory.getLogger(LogicalCouplingDecompositor.class);
	
	@Autowired
	HistoryService analysisService;
	
	@Autowired
	LogicalCouplingEngine logicalCouplingEngine;
	
	@Autowired
	LogicalCouplingToMicroserviceMapper logicalCouplingToMicroserviceMapper;
	
	@Autowired
	GraphMapper graphMapper;
	
	@Autowired
	GraphToMicrserviceMapper graphToMicroserviceMapper;
	
	@Override
	public void decompose(GitRepository repo) {
		try{
			logger.info("Computing history...");
			List<ChangeEvent> changeHistory = analysisService.computeChangeEvents(repo);
			
			logger.info("Computing logical couplings...");
			List<LogicalCoupling> couplings = logicalCouplingEngine.computeCouplings(changeHistory, 60000);
			logicalCouplingEngine.reset();
			
			logger.info("Computing nodes...");
			List<ClassNode> nodes = graphMapper.mapToGraph(couplings); 
			
			nodes.forEach(n -> logger.info(n.toString()));
			
			logger.info("Computing microservices...");
			List<Microservice> microservices = graphToMicroserviceMapper.mapToMicroservices(nodes);

			microservices.forEach(m -> logger.info(m.toString()));

			TextFileReport.generate(repo, microservices);
			logger.info("Finished.");
			
		}catch(Exception e){
			logger.error(e.getStackTrace().toString());
		}
		
	}
	
}
