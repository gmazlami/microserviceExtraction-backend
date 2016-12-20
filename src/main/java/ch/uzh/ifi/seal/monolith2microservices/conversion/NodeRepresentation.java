package ch.uzh.ifi.seal.monolith2microservices.conversion;

import java.io.File;

/**
 * Created by gmazlami on 12/20/16.
 */
public class NodeRepresentation {

    private long id;

    private String label;

    public NodeRepresentation(long id, String label){
        this.id = id;
        this.label = getClassNameFromFileName(label);
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

    private String getClassNameFromFileName(String fileName){
        String[] elements = fileName.split(File.separator);
        return elements[elements.length -1];
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
