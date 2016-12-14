package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.Decompositor;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.GraphToMicroserviceMapper;
import ch.uzh.ifi.seal.monolith2microservices.services.git.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogicalCouplingDecompositor implements Decompositor {

	private static final Logger logger = LoggerFactory.getLogger(LogicalCouplingDecompositor.class);
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	LogicalCouplingEngine logicalCouplingEngine;

	@Override
	public void decompose(GitRepository repo) {
		try{
			logger.info("Computing history...");
			List<ChangeEvent> changeHistory = historyService.computeChangeEvents(repo);
			logger.info("Successfully computed history!");

			logger.info("Computing logical couplings...");
			List<LogicalCoupling> couplings = logicalCouplingEngine.computeCouplings(changeHistory, 3600);
			logger.info("Successfully computed logical couplings!");

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getStackTrace().toString());
		}
		
	}
	
}
