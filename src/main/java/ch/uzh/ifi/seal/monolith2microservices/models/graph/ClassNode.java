package ch.uzh.ifi.seal.monolith2microservices.models.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class ClassNode {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long primaryKey;

	private String id;

	@JsonIgnore
	@Transient
	private boolean visited;

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
	private List<NodeWeightPair> neighbors;

	public ClassNode(){
		this.visited = false;
		this.neighbors = new ArrayList<>();
	}

	public ClassNode(String id){
		this.id = id;
		this.visited = false;
		this.neighbors = new ArrayList<>();
	}

	public Long getPrimaryKey(){
		return this.primaryKey;
	}

	public void setPrimaryKey(long key){
		this.primaryKey = key;
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

	public String getClassName(){
		String[] elements = id.split(File.separator);
		return elements[elements.length -1];
	}


	@JsonIgnore
	public List<NodeWeightPair> getNeighbors() {
		return neighbors;
	}

	@JsonIgnore
	public int getDegree(){
		return this.neighbors.size();
	}

	@JsonIgnore
	public double getCombinedWeight(){
        return this.neighbors.stream().mapToDouble(n -> {
            return n.getWeight();
        }).sum();
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClassNode)) return false;

		ClassNode classNode = (ClassNode) o;

		return id.equals(classNode.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
