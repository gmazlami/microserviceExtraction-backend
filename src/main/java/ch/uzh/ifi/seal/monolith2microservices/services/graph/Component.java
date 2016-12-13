package ch.uzh.ifi.seal.monolith2microservices.services.graph;

import ch.uzh.ifi.seal.monolith2microservices.models.git.Class;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genc on 13.12.2016.
 */
public class Component {

    private List<ClassNode> nodes;

    private boolean visited;

    public Component(){
        nodes = new ArrayList<>();
        visited = false;
    }

    public void addNode(ClassNode node){
        nodes.add(node);
    }

    public void setVisited(boolean value){
        this.visited = value;
    }

    public boolean getVisited(){
        return this.visited;
    }

    public List<ClassNode> getNodes(){
        return this.nodes;
    }

    @Override
    public String toString() {
        return "Component{" +
                "nodes=" + nodes +
                '}';
    }
}
