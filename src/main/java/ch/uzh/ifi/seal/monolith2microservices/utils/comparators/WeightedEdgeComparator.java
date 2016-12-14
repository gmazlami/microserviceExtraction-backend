package ch.uzh.ifi.seal.monolith2microservices.utils.comparators;


import ch.uzh.ifi.seal.monolith2microservices.models.graph.WeightedEdge;

import java.util.Comparator;


/**
 * Created by Genc on 09.12.2016.
 */
public class WeightedEdgeComparator implements Comparator<WeightedEdge> {

    @Override
    public int compare(WeightedEdge o1, WeightedEdge o2) {
        return new Double(o1.getScore()).compareTo(o2.getScore());
    }
}
