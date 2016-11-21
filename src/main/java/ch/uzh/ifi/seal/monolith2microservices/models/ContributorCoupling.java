package ch.uzh.ifi.seal.monolith2microservices.models;

import java.util.List;

/**
 * Created by gmazlami on 11/21/16.
 */
public class ContributorCoupling {

    private String firstFileName;

    private String secondFileName;

    private List<String> firstFileAuthors;

    private List<String> secondFileAuthors;

    private int score;

    public String getFirstFileName() {
        return firstFileName;
    }

    public void setFirstFileName(String firstFileName) {
        this.firstFileName = firstFileName;
    }

    public String getSecondFileName() {
        return secondFileName;
    }

    public void setSecondFileName(String secondFileName) {
        this.secondFileName = secondFileName;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
