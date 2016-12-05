package ch.uzh.ifi.seal.monolith2microservices.models.graph;

public class NodeWeightPair {

	private ClassNode node;
	private double weight;

	public NodeWeightPair(ClassNode node, double weight) {
		this.node = node;
		this.weight = weight;
	}

	public ClassNode getNode() {
		return node;
	}
	
	public String getNodeId(){
		return node.getId();
	}

	public void setNode(ClassNode node) {
		this.node = node;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "NodeWeightPair [node=" + node.getId() + ", weight=" + weight + "]";
	}
	
	
}