package ch.uzh.ifi.seal.monolith2microservices.models.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by gmazlami on 1/15/17.
 */

@Entity
public class MicroserviceMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection
    private Set<String> contributors;

    private int LOC;

    @OneToOne(cascade={CascadeType.REMOVE})
    private Component microservice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
