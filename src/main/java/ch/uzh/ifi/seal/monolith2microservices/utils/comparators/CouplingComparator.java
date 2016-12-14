package ch.uzh.ifi.seal.monolith2microservices.utils.comparators;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;

import java.util.Comparator;

/**
 * Created by Genc on 09.12.2016.
 */
public class CouplingComparator implements Comparator<BaseCoupling>{

    @Override
    public int compare(BaseCoupling o1, BaseCoupling o2) {
        return new Double(o1.getScore()).compareTo(o2.getScore());
    }
}
