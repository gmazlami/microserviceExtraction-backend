package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph;

public class Edge {

	private ClassNode source;
	
	private ClassNode target;
	
	private int weight;
	
	public Edge(ClassNode source, ClassNode target, int weight){
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	public ClassNode getSource() {
		return source;
	}

	public void setSource(ClassNode source) {
		this.source = source;
	}

	public ClassNode getTarget() {
		return target;
	}

	public void setTarget(ClassNode target) {
		this.target = target;
	}
	
	public int getWeight(){
		return this.weight;
	}
	
}
