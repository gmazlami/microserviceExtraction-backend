package ch.uzh.ifi.seal.monolith2microservices.utils.comparators;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.util.Comparator;

/**
 * Created by gmazlami on 12/15/16.
 */
public class ComponentComparator implements Comparator<Component> {

    @Override
    public int compare(Component o1, Component o2) {
        return new Integer(o1.getSize()).compareTo(o2.getSize());
    }
}
