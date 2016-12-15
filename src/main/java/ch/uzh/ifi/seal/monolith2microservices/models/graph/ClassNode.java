package ch.uzh.ifi.seal.monolith2microservices.models.graph;

import java.util.ArrayList;
import java.util.Iterator;
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

	public int getDegree(){
		return this.neighbors.size();
	}

	public void addNeighborWithWeight(ClassNode neighbor, int weight){
		neighbors.add(new NodeWeightPair(neighbor, (double) weight));
	}

	public void deleteNeighborWithId(String id){
		for(Iterator<NodeWeightPair> iterator = neighbors.iterator(); iterator.hasNext();){
			if (iterator.next().getNodeId().equals(id)){
				iterator.remove();
				return;
			}
		}
	}

	public void addNeighborWithWeight(ClassNode neighbor, double score){
		neighbors.add(new NodeWeightPair(neighbor, score));
	}

	@Override
	public String toString() {
		return "ClassNode [id=" + id + ", neighbors=" + neighbors + "]";
	}	
	
	
	
	
}
