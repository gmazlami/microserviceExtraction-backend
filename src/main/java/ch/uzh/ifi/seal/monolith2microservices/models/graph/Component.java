package ch.uzh.ifi.seal.monolith2microservices.models.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public int getSize() {
        return this.nodes.size();
    }

    @Override
    public String toString() {
        return "Component{" +
                "nodes=" + nodes.stream().map(n -> " , " + n.getId()).reduce("", String::concat) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;

        Component component = (Component) o;

        return nodes.equals(component.nodes);

    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
}
