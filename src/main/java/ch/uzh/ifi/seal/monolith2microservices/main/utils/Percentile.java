package ch.uzh.ifi.seal.monolith2microservices.main.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;

public class Percentile {

	private List<Integer> values;
	private List<Double> doubleVals;

	public static Percentile fromLogicalCouplings(List<LogicalCoupling> couplings){
		List<Double> vals = new ArrayList<>();
		couplings.forEach(c ->{
			vals.add(c.getScore());
		});
		return new Percentile(true,vals);
    }

    public static Percentile fromContributorCouplings(List<ContributorCoupling> couplings){
		List<Double> vals = new ArrayList<>();
		couplings.forEach(c ->{
			vals.add(c.getScore());
		});
		return new Percentile(true,vals);	}

	public static Percentile fromSemanticCouplings(List<SemanticCoupling> couplings){
		List<Double> vals = new ArrayList<>();
		couplings.forEach(c ->{
			vals.add(c.getScore());
		});
		return new Percentile(true,vals);
	}

	public Percentile(List<Integer> values){
        this.values = values;
        Collections.sort(this.values);
    }

    public Percentile(boolean isDouble, List<Double> values){
		this.doubleVals = values;
		Collections.sort(this.doubleVals);
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

	public double getDouble(float percentile){
		if(percentile > 1.0f){
			return this.doubleVals.get(this.doubleVals.size()-1);
		}else if(percentile < 0.0f){
			return this.doubleVals.get(0);
		}else{
			int q1Index = (int) Math.round(doubleVals.size() * percentile);
			return doubleVals.get(q1Index);
		}
	}
}
