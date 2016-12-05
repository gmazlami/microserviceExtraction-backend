package ch.uzh.ifi.seal.monolith2microservices.models.graph;

public class Edge {

	private ClassNode source;
	
	private ClassNode target;
	
	private double weight;
	
	public Edge(ClassNode source, ClassNode target, double weight){
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
	
	public double getWeight(){
		return this.weight;
	}
	
}
