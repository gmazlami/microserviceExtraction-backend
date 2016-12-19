package ch.uzh.ifi.seal.monolith2microservices.utils.comparators;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;

import java.util.Comparator;

/**
 * Created by gmazlami on 12/15/16.
 */
public class ClassNodeComparator implements Comparator<ClassNode> {

    @Override
    public int compare(ClassNode o1, ClassNode o2) {
        if(o1.getDegree() == o2.getDegree()){
            return new Double(o1.getCombinedWeight()).compareTo(o2.getCombinedWeight());
        }else{
            return new Integer(o1.getDegree()).compareTo(o2.getDegree());
        }
    }
}
