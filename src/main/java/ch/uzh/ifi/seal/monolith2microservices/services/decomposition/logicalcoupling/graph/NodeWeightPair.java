package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph;

public class NodeWeightPair {

	private ClassNode node;
	private int weight;

	public NodeWeightPair(ClassNode node, int weight) {
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "NodeWeightPair [node=" + node.getId() + ", weight=" + weight + "]";
	}
	
	
}