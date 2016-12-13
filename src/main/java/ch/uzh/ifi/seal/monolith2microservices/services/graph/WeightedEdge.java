package ch.uzh.ifi.seal.monolith2microservices.services.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by gmazlami on 12/8/16.
 */
public class WeightedEdge extends DefaultWeightedEdge {
        private static final long serialVersionUID = 708706467350994234L;

        private double score = 1.0D;

        public double getScore() {
            return this.score;
        }

        public void setScore(double score){
            this.score = score;
        }

        public String getFirstFileName(){
            return (String) this.getSource();
        }

        public String getSecondFileName(){
            return (String) this.getTarget();
        }

    @Override
    public String toString() {
        return "WeightedEdge{" +
                "score=" + score + "  nodes:" + this.getSource() + " : " + this.getTarget()+ "}";
    }

}
