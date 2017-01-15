package ch.uzh.ifi.seal.monolith2microservices.models.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;

/**
 * Created by gmazlami on 1/12/17.
 */
public class EvaluationMetrics {

    private Decomposition decomposition;

    private double contributorsPerMicroservice;

    private double contributorOverlapping;

    private double averageLoc;

    private double averageClassNumber;

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

    public double getAverageLoc() {
        return averageLoc;
    }

    public void setAverageLoc(double averageLoc) {
        this.averageLoc = averageLoc;
    }

    public double getAverageClassNumber() {
        return averageClassNumber;
    }

    public void setAverageClassNumber(double averageClassNumber) {
        this.averageClassNumber = averageClassNumber;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
