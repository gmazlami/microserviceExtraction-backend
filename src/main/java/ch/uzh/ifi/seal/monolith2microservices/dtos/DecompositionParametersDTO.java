package ch.uzh.ifi.seal.monolith2microservices.dtos;

/**
 * Created by gmazlami on 12/15/16.
 */
public class DecompositionParametersDTO {

    private boolean logicalCoupling;

    private boolean semanticCoupling;

    private boolean contributorCoupling;

    private int numServices;

    private int intervalSeconds;

    private int sizeThreshold;

    public boolean isLogicalCoupling() {
        return logicalCoupling;
    }

    public void setLogicalCoupling(boolean logicalCoupling) {
        this.logicalCoupling = logicalCoupling;
    }

    public boolean isSemanticCoupling() {
        return semanticCoupling;
    }

    public void setSemanticCoupling(boolean semanticCoupling) {
        this.semanticCoupling = semanticCoupling;
    }

    public boolean isContributorCoupling() {
        return contributorCoupling;
    }

    public void setContributorCoupling(boolean contributorCoupling) {
        this.contributorCoupling = contributorCoupling;
    }

    public int getNumServices() {
        return numServices;
    }

    public void setNumServices(int numServices) {
        this.numServices = numServices;
    }

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public int getSizeThreshold() {
        return sizeThreshold;
    }

    public void setSizeThreshold(int sizeThreshold) {
        this.sizeThreshold = sizeThreshold;
    }

    @Override
    public String toString() {
        return "DecompositionParametersDTO{" +
                "logicalCoupling=" + logicalCoupling +
                ", semanticCoupling=" + semanticCoupling +
                ", contributorCoupling=" + contributorCoupling +
                ", numServices=" + numServices +
                ", intervalSeconds=" + intervalSeconds +
                ", sizeThreshold=" + sizeThreshold +
                '}';
    }
}
