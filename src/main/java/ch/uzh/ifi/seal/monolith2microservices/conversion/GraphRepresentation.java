package ch.uzh.ifi.seal.monolith2microservices.conversion;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.util.Set;

/**
 * Created by gmazlami on 12/20/16.
 */
public class GraphRepresentation {

    private static long componentCounter = 1;

    private Set<NodeRepresentation> nodes;

    private Set<EdgeRepresentation> edges;

    private long componentId;

    public GraphRepresentation(Set<NodeRepresentation> nodes, Set<EdgeRepresentation> edges, long id){
        this.nodes = nodes;
        this.edges = edges;
        this.componentId = id;
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

    public void setComponentId(long id){
        this.componentId = id;
    }

    public long getComponentId(){
        return this.componentId;
    }



    public static GraphRepresentation from(Component component){
        Set<NodeRepresentation> nodes = GraphRepresentationConverter.convertNodes(component);
        Set<EdgeRepresentation> edges = GraphRepresentationConverter.convertEdges(component, nodes);
        return new GraphRepresentation(nodes,edges, componentCounter++);
    }
}
