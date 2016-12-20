package ch.uzh.ifi.seal.monolith2microservices.dtos;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gmazlami on 12/20/16.
 */
public class NodeRepresentation {

    private long id;

    private String label;

    public NodeRepresentation(long id, String label){
        this.id = id;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeRepresentation)) return false;

        NodeRepresentation that = (NodeRepresentation) o;

        if (id != that.id) return false;
        return label.equals(that.label);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + label.hashCode();
        return result;
    }
}
