package ch.uzh.ifi.seal.monolith2microservices.models.couplings;

import java.util.List;

/**
 * Created by gmazlami on 11/21/16.
 */
public class ContributorCoupling extends BaseCoupling{

    private List<String> firstFileAuthors;

    private List<String> secondFileAuthors;


    public ContributorCoupling(String firstFileName, String secondFileName, double score) {
        super(firstFileName, secondFileName, score);
    }

    public List<String> getFirstFileAuthors() {
        return firstFileAuthors;
    }

    public void setFirstFileAuthors(List<String> firstFileAuthors) {
        this.firstFileAuthors = firstFileAuthors;
    }

    public List<String> getSecondFileAuthors() {
        return secondFileAuthors;
    }

    public void setSecondFileAuthors(List<String> secondFileAuthors) {
        this.secondFileAuthors = secondFileAuthors;
    }

    @Override
    public String toString() {
        return "ContributorCoupling{" +
                "firstFileName='" + firstFileName + '\'' +
                ", secondFileName='" + secondFileName + '\'' +
                ", score=" + score +
                '}';
    }
}
