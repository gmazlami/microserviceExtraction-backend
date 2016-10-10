package ch.uzh.ifi.seal.monolith2microservices.main.utils;

import java.util.Comparator;

import ch.uzh.ifi.seal.monolith2microservices.models.LogicalCoupling;

public class LogicalCouplingComparator implements Comparator<LogicalCoupling> {

	@Override
	public int compare(LogicalCoupling o1, LogicalCoupling o2) {
		return new Integer(o1.getScore()).compareTo(new Integer(o2.getScore()));
	}

}
