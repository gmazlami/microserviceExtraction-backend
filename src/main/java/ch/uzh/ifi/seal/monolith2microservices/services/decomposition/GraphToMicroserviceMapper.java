package ch.uzh.ifi.seal.monolith2microservices.services.decomposition;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Edge;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.NodeWeightPair;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Microservice;

@Service
public class GraphToMicroserviceMapper {
	
	
	public List<Microservice> mapToMicroservices(List<ClassNode> nodes){
		List<Microservice> microservices = new ArrayList<>();
		
		for(ClassNode node : nodes){
			if(!node.isVisited()){
				Microservice m = new Microservice();
				node.setVisited(true);
				m.addClass(node.getId());
				dfs(node,m);
				microservices.add(m);
			}
		}
		
		return microservices;
	}
	
	
	private void dfs(ClassNode node, Microservice microservice){
		for(NodeWeightPair neighbor : node.getNeighbors()){
			if(!neighbor.getNode().isVisited()){
				neighbor.getNode().setVisited(true);
				microservice.addClass(neighbor.getNode().getId());
				microservice.addEdge(new Edge(node, neighbor.getNode(), neighbor.getWeight()));

				dfs(neighbor.getNode(), microservice);
			}
		}
	}

}
