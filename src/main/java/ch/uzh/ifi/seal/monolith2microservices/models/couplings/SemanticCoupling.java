package ch.uzh.ifi.seal.monolith2microservices.models.couplings;

/**
 * Created by gmazlami on 11/30/16.
 */
public class SemanticCoupling {

    private double similarity;

    private String firstClassFileName;

    private String secondClassFileName;


    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public String getFirstClassFileName() {
        return firstClassFileName;
    }

    public void setFirstClassFileName(String firstClassFileName) {
        this.firstClassFileName = firstClassFileName;
    }

    public String getSecondClassFileName() {
        return secondClassFileName;
    }

    public void setSecondClassFileName(String secondClassFileName) {
        this.secondClassFileName = secondClassFileName;
    }
}
