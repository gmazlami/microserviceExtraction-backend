package ch.uzh.ifi.seal.monolith2microservices.main.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;

public class Percentile {
	
	private List<LogicalCoupling> couplings;

	private List<Integer> values;

	public static Percentile fromLogicalCouplings(List<LogicalCoupling> couplings){
        List<Integer> vals = new ArrayList<>();
        couplings.forEach(c -> {
            vals.add(c.getScore());
        });
        return new Percentile(vals);
    }


	public Percentile(List<Integer> values){
        this.values = values;
        Collections.sort(this.values);
    }
	
	public int get(float percentile){
		if(percentile > 1.0f){
			return this.values.get(this.values.size()-1);
		}else if(percentile < 0.0f){
			return this.values.get(0);
		}else{
			int q1Index = (int) Math.round(values.size() * percentile);
			return values.get(q1Index);
		}
	}
	
}
