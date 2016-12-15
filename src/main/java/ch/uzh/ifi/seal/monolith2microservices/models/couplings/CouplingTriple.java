package ch.uzh.ifi.seal.monolith2microservices.models.couplings;

/**
 * Created by gmazlami on 12/15/16.
 */
public class CouplingTriple {


    private String firstFile;

    private String secondFile;

    private LogicalCoupling logicalCoupling;

    private SemanticCoupling semanticCoupling;

    private ContributorCoupling contributorCoupling;

    public LogicalCoupling getLogicalCoupling() {
        return logicalCoupling;
    }

    public void setLogicalCoupling(LogicalCoupling logicalCoupling) {
        this.logicalCoupling = logicalCoupling;
    }

    public SemanticCoupling getSemanticCoupling() {
        return semanticCoupling;
    }

    public void setSemanticCoupling(SemanticCoupling semanticCoupling) {
        this.semanticCoupling = semanticCoupling;
    }

    public ContributorCoupling getContributorCoupling() {
        return contributorCoupling;
    }

    public void setContributorCoupling(ContributorCoupling contributorCoupling) {
        this.contributorCoupling = contributorCoupling;
    }

    public String getFirstFile() {
        return firstFile;
    }

    public void setFirstFile(String firstFile) {
        this.firstFile = firstFile;
    }

    public String getSecondFile() {
        return secondFile;
    }

    public void setSecondFile(String secondFile) {
        this.secondFile = secondFile;
    }

}
