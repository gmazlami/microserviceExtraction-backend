package ch.uzh.ifi.seal.monolith2microservices.models.couplings;

/**
 * Created by Genc on 08.12.2016.
 */
public abstract class BaseCoupling {

    protected String firstFileName;

    protected String secondFileName;

    protected double score;

    public BaseCoupling(String firstFileName, String secondFileName, double score){
        this.firstFileName = firstFileName;
        this.secondFileName = secondFileName;
        this.score = score;
    }

    public String getFirstFileName(){
        return this.firstFileName;
    }

    public String getSecondFileName(){
        return this.secondFileName;
    }

    public double getScore(){
        return this.score;
    }

    public void setFirstFileName(String fileName){
        this.firstFileName = fileName;
    }

    public void setSecondFileName(String secondName){
        this.secondFileName = secondName;
    }

    public void setScore(double score){
        this.score = score;
    }

}
