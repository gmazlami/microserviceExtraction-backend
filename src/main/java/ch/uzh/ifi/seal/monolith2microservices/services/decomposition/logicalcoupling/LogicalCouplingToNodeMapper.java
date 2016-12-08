package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.Percentile;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogicalCouplingToNodeMapper {

	private Map<String, ClassNode> nodeMap;
	
	public List<ClassNode> mapToGraph(List<LogicalCoupling> couplings){
		nodeMap = new HashMap<>();
		double lowerBound = Percentile.fromLogicalCouplings(couplings).getDouble(0.8f);
		
		for(LogicalCoupling coupling: couplings){
			if(coupling.getScore() > lowerBound){

				ClassNode firstNode = nodeMap.get(coupling.getFirstFileName());
				ClassNode secondNode = nodeMap.get(coupling.getSecondFileName());

				if((firstNode == null) && (secondNode == null)){
					
					firstNode = new ClassNode(coupling.getFirstFileName());
					secondNode = new ClassNode(coupling.getSecondFileName());
					nodeMap.put(coupling.getFirstFileName(), firstNode);
					nodeMap.put(coupling.getSecondFileName(), secondNode);
					
				}else if((firstNode != null) && (secondNode == null)){
					
					secondNode = new ClassNode(coupling.getSecondFileName());
					nodeMap.put(coupling.getSecondFileName(), secondNode);
					
				}else if((firstNode == null) && (secondNode != null)){
					
					firstNode = new ClassNode(coupling.getFirstFileName());
					nodeMap.put(coupling.getFirstFileName(), firstNode);
					
				}

				firstNode.addNeighborWithWeight(secondNode, coupling.getScore());
				secondNode.addNeighborWithWeight(firstNode, coupling.getScore());
			}
		}
		
		return nodeMap.values().stream().collect(Collectors.toList());
	}

}
