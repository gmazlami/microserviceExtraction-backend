package ch.uzh.ifi.seal.monolith2microservices.models.couplings;

/**
 * Created by gmazlami on 12/15/16.
 */
public interface Coupling {

    String getFirstFileName();

    String getSecondFileName();

    double getScore();
}
