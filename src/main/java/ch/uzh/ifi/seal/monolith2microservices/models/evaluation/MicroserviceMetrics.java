package ch.uzh.ifi.seal.monolith2microservices.models.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import java.util.Set;

/**
 * Created by gmazlami on 1/15/17.
 */
public class MicroserviceMetrics {

    private Set<String> contributors;

    private int LOC;

    private Component microservice;



    public MicroserviceMetrics(Component microservice){
        this.microservice = microservice;
    }

    public void setContributors(Set<String> contributors){
        this.contributors = contributors;
    }

    public void setSizeLoc(int size){
        this.LOC = size;
    }

    public Set<String> getContributors(){
        return this.contributors;
    }

    public int getNumOfContributors(){
        return this.contributors.size();
    }

    public int getSizeInLoc(){
        return this.LOC;
    }

    public int getSizeInClasses(){
        return this.microservice.getNodes().size();
    }
}
