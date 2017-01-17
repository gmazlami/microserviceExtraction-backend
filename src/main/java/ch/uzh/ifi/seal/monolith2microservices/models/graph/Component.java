package ch.uzh.ifi.seal.monolith2microservices.models.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Genc on 13.12.2016.
 */

@Entity
public class Component {

    @Id
    private Long id;

    @OneToMany
    private List<ClassNode> nodes;

    @JsonIgnore
    @Transient
    private boolean visited;

    public Component(){
        id = new Random().nextLong() % System.currentTimeMillis();
        nodes = new ArrayList<>();
        visited = false;
    }

    public Long getId(){
        return this.id;
    }

    public void addNode(ClassNode node){
        nodes.add(node);
    }

    public void setVisited(boolean value){
        this.visited = value;
    }

    @JsonIgnore
    public boolean getVisited(){
        return this.visited;
    }

    public List<ClassNode> getNodes(){
        return this.nodes;
    }

    @JsonIgnore
    public int getSize() {
        return this.nodes.size();
    }


    @JsonIgnore
    public List<String> getFilePaths(){
        return this.nodes.stream().map(classNode -> classNode.getId()).collect(Collectors.toList());
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
