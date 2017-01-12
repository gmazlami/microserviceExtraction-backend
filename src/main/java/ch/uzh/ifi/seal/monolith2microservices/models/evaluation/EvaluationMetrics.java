package ch.uzh.ifi.seal.monolith2microservices.models.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;

/**
 * Created by gmazlami on 1/12/17.
 */
public class EvaluationMetrics {

    private Decomposition decomposition;

    private double contributorsPerMicroservice;

    private double contributorOverlapping;

    private long sizeKLOC;

    private int sizeClasses;

    private double similarity;

    public Decomposition getDecomposition() {
        return decomposition;
    }

    public void setDecomposition(Decomposition decomposition) {
        this.decomposition = decomposition;
    }

    public double getContributorsPerMicroservice() {
        return contributorsPerMicroservice;
    }

    public void setContributorsPerMicroservice(double contributorsPerMicroservice) {
        this.contributorsPerMicroservice = contributorsPerMicroservice;
    }

    public double getContributorOverlapping() {
        return contributorOverlapping;
    }

    public void setContributorOverlapping(double contributorOverlapping) {
        this.contributorOverlapping = contributorOverlapping;
    }

    public long getSizeKLOC() {
        return sizeKLOC;
    }

    public void setSizeKLOC(long sizeKLOC) {
        this.sizeKLOC = sizeKLOC;
    }

    public int getSizeClasses() {
        return sizeClasses;
    }

    public void setSizeClasses(int sizeClasses) {
        this.sizeClasses = sizeClasses;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
