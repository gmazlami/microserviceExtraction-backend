package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {
	
	private String id;
	
	private boolean visited;
	
	private List<NodeWeightPair> neighbors;

	public ClassNode(String id){
		this.id = id;
		this.visited = false;
		this.neighbors = new ArrayList<>();
	}
	
	public ClassNode(String id, boolean visited){
		this.id = id;
		this.visited = visited;
		this.neighbors = new ArrayList<>();
	}
	
	
	public void setNeighborWeight(String neighborId, int newWeight){
		this.neighbors.forEach(neighbor -> {
			if (neighbor.getNodeId() == neighborId){
				neighbor.setWeight(newWeight);
			}
		});
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public boolean isVisited() {
		return visited;
	}


	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	public List<NodeWeightPair> getNeighbors() {
		return neighbors;
	}


	public void setNeighbors(List<NodeWeightPair> neighbors) {
		this.neighbors = neighbors;
	}


	public void addNeighborWithWeight(ClassNode neighbor, int weight){
		neighbors.add(new NodeWeightPair(neighbor, weight));
	}
	
	@Override
	public String toString() {
		return "ClassNode [id=" + id + ", visited=" + visited + ", neighbors=" + neighbors + "]";
	}	
	
	
	
	
}
