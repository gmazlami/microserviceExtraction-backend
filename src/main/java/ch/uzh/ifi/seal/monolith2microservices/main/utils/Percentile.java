package ch.uzh.ifi.seal.monolith2microservices.main.utils;

import java.util.Collections;
import java.util.List;

import ch.uzh.ifi.seal.monolith2microservices.models.LogicalCoupling;

public class Percentile {
	
	private List<LogicalCoupling> couplings;
	
	public Percentile(List<LogicalCoupling> couplings){
		this.couplings = couplings;
		Collections.sort(this.couplings, new LogicalCouplingComparator());
	}
	
	public int get(float percentile){
		if(percentile > 1.0f){
			return this.couplings.get(this.couplings.size()-1).getScore();
		}else if(percentile < 0.0f){
			return this.couplings.get(0).getScore();
		}else{
			int q1Index = (int) Math.round(couplings.size() * 0.75f);
			return couplings.get(q1Index).getScore();
		}
	}
	
}
