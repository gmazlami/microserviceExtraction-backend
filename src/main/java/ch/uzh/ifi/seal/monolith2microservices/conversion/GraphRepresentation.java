package ch.uzh.ifi.seal.monolith2microservices.conversion;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.util.Set;

/**
 * Created by gmazlami on 12/20/16.
 */
public class GraphRepresentation {

    private Set<NodeRepresentation> nodes;

    private Set<EdgeRepresentation> edges;

    public GraphRepresentation(Set<NodeRepresentation> nodes, Set<EdgeRepresentation> edges){
        this.nodes = nodes;
        this.edges = edges;
    }

    public Set<NodeRepresentation> getNodes() {
        return nodes;
    }

    public void setNodes(Set<NodeRepresentation> nodes) {
        this.nodes = nodes;
    }

    public Set<EdgeRepresentation> getEdges() {
        return edges;
    }

    public void setEdges(Set<EdgeRepresentation> edges) {
        this.edges = edges;
    }

    public static GraphRepresentation from(Set<Component> components){
        Set<NodeRepresentation> nodes = GraphRepresentationConverter.convertNodes(components);
        Set<EdgeRepresentation> edges = GraphRepresentationConverter.convertEdges(components, nodes);
        return new GraphRepresentation(nodes,edges);
    }
}
